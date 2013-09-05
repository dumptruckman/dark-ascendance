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

    private static final Integer DEFAULT_RESEND_TIMEOUT = 500;

    private KryoNetwork kryoNetwork;
    MessageResequencer resequencer;

    private Set<Integer> potentialConnections = Collections.newSetFromMap(new ConcurrentHashMap<Integer, Boolean>());
    private Map<Integer, Integer> resendTimeoutMap = new ConcurrentHashMap<Integer, Integer>();
    private Map<Integer, Map<Short, Acknowledgement>> receivedAcknowledgements = new ConcurrentHashMap<Integer, Map<Short, Acknowledgement>>();
    private Queue<Message> incomingMessageQueue = new ConcurrentLinkedQueue<Message>();

    public MessageReceiver(KryoNetwork kryoNetwork) {
        this.kryoNetwork = kryoNetwork;
        resequencer = new MessageResequencer();
    }

    public void receiveMessage(Integer connectionId, Message message, int returnTripTime) {
        handleConnectDisconnect(connectionId, message);
        if (isPotentialConnection(connectionId)) {
            updateTimeoutForConnection(connectionId, returnTripTime);
            sendAcknowledgementIfAppropriate(connectionId, message);
            if (message.isImportant()) {
                resequencer.ensureMessageOrder(connectionId, message);
                while (resequencer.hasOrderlyMessage(connectionId)) {
                    Message nextOrderlyMessage = resequencer.getNextOrderlyMessage(connectionId);
                    incomingMessageQueue.add(nextOrderlyMessage);
                }
            } else {
                incomingMessageQueue.add(message);
            }
        }
    }

    public void receiveAcknowledgement(int connectionId, Acknowledgement acknowledgement) {
        Map<Short, Acknowledgement> acknowledgementMap = receivedAcknowledgements.get(connectionId);
        if (acknowledgementMap != null) {
            acknowledgementMap.put(acknowledgement.getMessageId(), acknowledgement);
        } else {
            System.out.println("Received and discarded acknowledgement " + acknowledgement.getMessageId() + " from disconnected client: " + connectionId);
        }
    }

    private void handleConnectDisconnect(int connectionId, Message message) {
        switch (message.getMessageType()) {
            case PLAYER_CONNECTED:
                addConnection(connectionId);
                break;
            case PLAYER_DISCONNECTED:
                removeConnection(connectionId);
                break;
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
            resendTimeoutMap.put(connectionId, 30);
        } else {
            resendTimeoutMap.put(connectionId, (int) (returnTripTime * 1.5));
        }
    }

    private void sendAcknowledgementIfAppropriate(int connectionId, Message message) {
        if (message.isImportant()
                && message.getMessageType() != MessageType.PLAYER_CONNECTED
                && message.getMessageType() != MessageType.PLAYER_DISCONNECTED) {
            kryoNetwork.sendAcknowledgement(connectionId, (Acknowledgement) new Acknowledgement().messageId(message.getMessageId()));
        }
    }

    public int getResendTimeout(Integer connectionId) {
        return resendTimeoutMap.get(connectionId);
    }

    public Message getNextIncomingMessage() {
        return incomingMessageQueue.poll();
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
