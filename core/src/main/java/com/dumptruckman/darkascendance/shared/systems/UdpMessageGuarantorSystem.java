package com.dumptruckman.darkascendance.shared.systems;

import com.badlogic.gdx.utils.IntMap;
import com.dumptruckman.darkascendance.shared.messages.Acknowledgement;
import com.dumptruckman.darkascendance.shared.messages.AcknowledgementBatch;
import com.dumptruckman.darkascendance.shared.messages.Message;
import com.dumptruckman.darkascendance.shared.messages.MessageBase;
import com.dumptruckman.darkascendance.shared.messages.MessageType;
import com.dumptruckman.darkascendance.shared.network.KryoNetwork;
import recs.EntitySystem;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class UdpMessageGuarantorSystem extends EntitySystem {

    private static final int DEFAULT_RESEND_TIMEOUT = 500;
    private static final short HALF_SHORT_MAX_VALUE = Short.MAX_VALUE / 2;

    private short outgoingMessageIdCounter = 0;
    private IntMap<Queue<Message>> importantOutgoingMessageQueues = new IntMap<Queue<Message>>();
    private IntMap<Map<Short, Long>> lastSentTimes = new IntMap<Map<Short, Long>>();
    private Map<Integer, Map<Short, Acknowledgement>> receivedAcknowledgementMaps = new ConcurrentHashMap<Integer, Map<Short, Acknowledgement>>();
    private Map<Integer, AcknowledgementBatch> outgoingAcknowledgements = new ConcurrentHashMap<Integer, AcknowledgementBatch>();
    private Map<Integer, Integer> resendTimeoutMap = new ConcurrentHashMap<Integer, Integer>();

    private Queue<Message> incomingMessageQueue = new ConcurrentLinkedQueue<Message>();

    private Set<Integer> potentialConnections = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());

    private KryoNetwork kryoNetwork;
    private long currentTime = 0L;

    public UdpMessageGuarantorSystem(KryoNetwork kryoNetwork) {
        this.kryoNetwork = kryoNetwork;
    }

    @Override
    protected void processSystem(float deltaInSec) {
        updateTime(deltaInSec);
        processIncomingMessages();
        sendAcknowledgements();
        resendMessagesIfNotAcknowledgedWithinTimeout();
    }

    private void updateTime(float deltaInSec) {
        currentTime += deltaInSec * 1000;
        kryoNetwork.setCurrentGameTime(currentTime);
    }

    private void processIncomingMessages() {
        Message message;
        while ((message = incomingMessageQueue.poll()) != null) {
            handleConnectOrDisconnect(message);
            kryoNetwork.handleMessage(message);
        }
    }

    private void handleConnectOrDisconnect(Message message) {
        if (message.getMessageType() == MessageType.PLAYER_CONNECTED) {
            addNewConnection(message.getConnectionId());
        } else if (message.getMessageType() == MessageType.PLAYER_DISCONNECTED) {
            removeOldConnection(message.getConnectionId());
        }
    }

    private void addNewConnection(Integer connectionId) {
        importantOutgoingMessageQueues.put(connectionId, new LinkedList<Message>());
        receivedAcknowledgementMaps.put(connectionId, new ConcurrentHashMap<Short, Acknowledgement>());
        resendTimeoutMap.put(connectionId, DEFAULT_RESEND_TIMEOUT);
        lastSentTimes.put(connectionId, new HashMap<Short, Long>());
    }

    private void removeOldConnection(Integer connectionId) {
        importantOutgoingMessageQueues.remove(connectionId);
        receivedAcknowledgementMaps.remove(connectionId);
        resendTimeoutMap.remove(connectionId);
        lastSentTimes.remove(connectionId);
    }

    private void sendAcknowledgements() {
        for (Map.Entry<Integer, AcknowledgementBatch> entry : outgoingAcknowledgements.entrySet()) {
            kryoNetwork.sendAcknowledgement(entry.getKey(), entry.getValue());
        }
        outgoingAcknowledgements.clear();
    }

    private void resendMessagesIfNotAcknowledgedWithinTimeout() {
        for (IntMap.Entry<Queue<Message>> outgoingMessagesEntry : importantOutgoingMessageQueues.entries()) {
            int connectionId = outgoingMessagesEntry.key;
            int timeout = resendTimeoutMap.get(connectionId);
            Queue<Message> outgoingMessageQueue = outgoingMessagesEntry.value;
            if (outgoingMessageQueue == null) {
                System.out.println("HOUSTON, WE HAVE A PROBLEM!");
            }
            Map<Short, Acknowledgement> acknowledgements = receivedAcknowledgementMaps.get(connectionId);
            Map<Short, Long> lastSentTimeMap = lastSentTimes.get(connectionId);

            Message outgoingMessage;
            while ((outgoingMessage = outgoingMessageQueue.peek()) != null
                    && currentTime - lastSentTimeMap.get(outgoingMessage.getMessageId()) > timeout) {
                outgoingMessage = outgoingMessagesEntry.value.poll();
                short outgoingMessageId = outgoingMessage.getMessageId();
                if (acknowledgements.containsKey(outgoingMessageId)) {
                    acknowledgements.remove(outgoingMessageId);
                    lastSentTimeMap.remove(outgoingMessageId);
                } else {
                    lastSentTimeMap.put(outgoingMessageId, currentTime);
                    outgoingMessageQueue.add(outgoingMessage);
                    kryoNetwork.resendMessage(connectionId, outgoingMessage);
                }
            }
        }
    }

    public void guaranteeMessage(Message message) {
        prepareMessageForSending(message);
        if (message.isImportant()) {
            addMessageToOutgoingQueue(message);
        }
    }

    private void prepareMessageForSending(Message message) {
        message.time(currentTime);
        if (message.isImportant()) {
            message.messageId(outgoingMessageIdCounter);
            setLastSentTime(message);
            incrementOutgoingMessageIdCounter();
        }
    }

    private void setLastSentTime(Message message) {
        for (Map<Short, Long> lastSentTimeMap : lastSentTimes.values()) {
            lastSentTimeMap.put(message.getMessageId(), currentTime);
        }
    }

    private void incrementOutgoingMessageIdCounter() {
        outgoingMessageIdCounter++;
        if (outgoingMessageIdCounter > HALF_SHORT_MAX_VALUE) {
            outgoingMessageIdCounter = 0;
            System.out.println("Reset outgoing message id counter");
        }
    }

    private void addMessageToOutgoingQueue(Message message) {
        for (Queue<Message> outgoingMessageQueue : this.importantOutgoingMessageQueues.values()) {
            outgoingMessageQueue.add(message);
        }
    }

    public void receiveMessage(int connectionId, MessageBase messageBase, int returnTripTime) {
        updateTimeoutForConnection(connectionId, returnTripTime);
        if (messageBase instanceof Acknowledgement) {
            Map<Short, Acknowledgement> acknowledgeMap = receivedAcknowledgementMaps.get(connectionId);
            if (acknowledgeMap != null) {
                acknowledgeMap.put(messageBase.getMessageId(), (Acknowledgement) messageBase);
            } else {
                System.out.println("Received and discarded acknowledgement " + messageBase.getMessageId() + " from disconnected client: " + connectionId);
            }
        } else if (messageBase instanceof Message) {
            Message message = (Message) messageBase;
            if (message.getMessageType() == MessageType.PLAYER_CONNECTED) {
                potentialConnections.add(connectionId);
            } else if (message.getMessageType() == MessageType.PLAYER_DISCONNECTED) {
                potentialConnections.remove(connectionId);
            }
            if (message.isImportant()
                    && message.getMessageType() != MessageType.PLAYER_CONNECTED
                    && message.getMessageType() != MessageType.PLAYER_DISCONNECTED) {
                AcknowledgementBatch batch = getAcknowledgementBatch(connectionId);
                if (batch != null) {
                    batch.addAcknowledgement((Acknowledgement) new Acknowledgement().messageId(message.getMessageId()));
                }
            }
            incomingMessageQueue.add(message);
        }
    }

    private void updateTimeoutForConnection(int connectionId, int returnTripTime) {
        if (returnTripTime == 0) {
            resendTimeoutMap.put(connectionId, 30);
        } else {
            resendTimeoutMap.put(connectionId, (int) (returnTripTime * 1.5));
        }
    }

    AcknowledgementBatch getAcknowledgementBatch(int connectionId) {
        AcknowledgementBatch batch = outgoingAcknowledgements.get(connectionId);
        if (batch == null) {
            if (potentialConnections.contains(connectionId)) {
                batch = new AcknowledgementBatch();
                outgoingAcknowledgements.put(connectionId, batch);
            }
        }
        return batch;
    }
}
