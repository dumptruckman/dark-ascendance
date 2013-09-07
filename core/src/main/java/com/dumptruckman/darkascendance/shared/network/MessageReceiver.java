package com.dumptruckman.darkascendance.shared.network;

import com.dumptruckman.darkascendance.shared.messages.Acknowledgement;
import com.dumptruckman.darkascendance.shared.messages.Message;
import com.dumptruckman.darkascendance.shared.messages.MessageType;

import java.util.Collections;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

class MessageReceiver {

    private static final long MS_TO_NS = 1000000L;
    private static final Long DEFAULT_RESEND_TIMEOUT = 500L;

    private KryoNetwork kryoNetwork;
    MessageResequencer resequencer;

    private Set<Integer> potentialConnections = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());
    private Map<Integer, Long> resendTimeoutMap = new ConcurrentHashMap<Integer, Long>();
    private Map<Integer, Map<Short, Acknowledgement>> receivedAcknowledgements = new ConcurrentHashMap<Integer, Map<Short, Acknowledgement>>();
    private Queue<Message> incomingMessageQueue = new ConcurrentLinkedQueue<Message>();

    public MessageReceiver(KryoNetwork kryoNetwork) {
        this.kryoNetwork = kryoNetwork;
        resequencer = new MessageResequencer();
    }

    public void receiveMessage(Integer connectionId, Message message, int returnTripTime) {
        checkPlayerConnect(message);
        if (isPotentialConnection(connectionId)) {
            updateTimeoutForConnection(connectionId, returnTripTime);
            sendAcknowledgementIfAppropriate(connectionId, message);
            if (message.isImportant()) {
                System.out.println("Received important message: " + message);
                resequencer.ensureMessageOrder(connectionId, message);
                while (resequencer.hasOrderlyMessage(connectionId)) {
                    Message nextOrderlyMessage = resequencer.getNextOrderlyMessage(connectionId);
                    incomingMessageQueue.add(nextOrderlyMessage);
                }
            } else {
                incomingMessageQueue.add(message);
            }
        } else {
            System.out.println("Received message from invalid connection: " + message);
        }
    }

    public void receiveAcknowledgement(int connectionId, Acknowledgement acknowledgement) {
        Map<Short, Acknowledgement> acknowledgementMap = receivedAcknowledgements.get(connectionId);
        if (acknowledgementMap != null) {
            System.out.println("Received " + acknowledgement);
            acknowledgementMap.put(acknowledgement.getMessageId(), acknowledgement);
        } else {
            System.out.println("Received and discarded acknowledgement " + acknowledgement.getMessageId() + " from disconnected connection: " + connectionId);
        }
    }

    private void checkPlayerConnect(Message message) {
        if (message.getMessageType() == MessageType.PLAYER_CONNECTED) {
            addConnection(message.getConnectionId());
        }
    }

    private void checkPlayerDisconnect(Message message) {
        if (message.getMessageType() == MessageType.PLAYER_DISCONNECTED) {
            removeConnection(message.getConnectionId());
        }
    }

    void addConnection(Integer connectionId) {
        potentialConnections.add(connectionId);
        resendTimeoutMap.put(connectionId, DEFAULT_RESEND_TIMEOUT);
        receivedAcknowledgements.put(connectionId, new ConcurrentHashMap<Short, Acknowledgement>());
        resequencer.addConnection(connectionId);
    }

    void removeConnection(Integer connectionId) {
        potentialConnections.remove(connectionId);
        resendTimeoutMap.remove(connectionId);
        receivedAcknowledgements.remove(connectionId);
        resequencer.removeConnection(connectionId);
    }

    private void updateTimeoutForConnection(int connectionId, int returnTripTime) {
        if (returnTripTime == 0) {
            resendTimeoutMap.put(connectionId, 30L * MS_TO_NS);
        } else {
            resendTimeoutMap.put(connectionId, ((long) ((returnTripTime * MS_TO_NS) * 1.5f)));
        }
    }

    private void sendAcknowledgementIfAppropriate(int connectionId, Message message) {
        if (message.isImportant()
                && message.getMessageType() != MessageType.PLAYER_CONNECTED
                && message.getMessageType() != MessageType.PLAYER_DISCONNECTED) {
            kryoNetwork.sendAcknowledgement(connectionId, (Acknowledgement) new Acknowledgement().messageId(message.getMessageId()));
        }
    }

    public long getResendTimeout(Integer connectionId) {
        return resendTimeoutMap.get(connectionId);
    }

    public Message getNextIncomingMessage() {
        Message message = incomingMessageQueue.poll();
        if (message != null) {
            checkPlayerDisconnect(message);
        }
        return message;
    }

    public boolean hasAcknowledgement(Integer connectionId, Short messageId) {
        Map<Short, Acknowledgement> acknowledgements = receivedAcknowledgements.get(connectionId);
        return acknowledgements != null && acknowledgements.containsKey(messageId);
    }

    public void removeAcknowledgement(Integer connectionId, Short messageId) {
        Map<Short, Acknowledgement> acknowledgements = receivedAcknowledgements.get(connectionId);
        if (acknowledgements != null) {
            acknowledgements.remove(messageId);
        }
    }

    public boolean isPotentialConnection(int connectionId) {
        return potentialConnections.contains(connectionId);
    }
}
