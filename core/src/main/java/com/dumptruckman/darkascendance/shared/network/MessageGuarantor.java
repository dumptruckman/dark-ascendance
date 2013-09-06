package com.dumptruckman.darkascendance.shared.network;

import com.badlogic.gdx.utils.IntMap;
import com.dumptruckman.darkascendance.shared.messages.Message;
import com.dumptruckman.darkascendance.shared.messages.MessageType;

import java.util.LinkedList;
import java.util.Queue;

class MessageGuarantor {

    static final short HALF_SHORT_MAX_VALUE = Short.MAX_VALUE / 2;

    private long currentTime = 0L;
    private IntMap<Short> outgoingMessageIdCounter = new IntMap<Short>();
    private IntMap<Queue<Message>> messageQueues = new IntMap<Queue<Message>>();
    private IntMap<IntMap<Long>> lastSentTimes = new IntMap<IntMap<Long>>();

    MessageGuarantor() { }

    public long getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(long time) {
        this.currentTime = time;
    }

    public void addConnection(int connectionId) {
        System.out.println("Adding conenction " + connectionId);
        messageQueues.put(connectionId, new LinkedList<Message>());
        lastSentTimes.put(connectionId, new IntMap<Long>());
        outgoingMessageIdCounter.put(connectionId, (short) 0);
    }

    public void removeConnection(int connectionId) {
        System.out.println("Removing conenction " + connectionId);
        messageQueues.remove(connectionId);
        lastSentTimes.remove(connectionId);
        outgoingMessageIdCounter.remove(connectionId);
    }

    public IntMap.Keys getConnectionsWithRemainingMessages() {
        return messageQueues.keys();
    }

    public boolean hasMessagesOlderThan(int connectionId, int timeout) {
        Message message = getMessageQueue(connectionId).peek();
        if (message != null) {
            float lastTimeSent = getTimeMessageLastSent(connectionId, message.getMessageId());
            float delta = currentTime - lastTimeSent;
            return delta > timeout;
        }
        return false;
    }

    private Queue<Message> getMessageQueue(int connectionId) {
        return messageQueues.get(connectionId);
    }

    private float getTimeMessageLastSent(int connectionId, short messageId) {
        return lastSentTimes.get(connectionId).get(messageId);
    }

    public Message getNextMessage(int connectionId) {
        return getMessageQueue(connectionId).poll();
    }

    public void notifyMessageAcknowledged(int connectionId, short messageId) {
        lastSentTimes.get(connectionId).remove(messageId);
    }

    public void updateGuarantee(int connectionId, Message message) {
        lastSentTimes.get(connectionId).put(message.getMessageId(), currentTime);
        getMessageQueue(connectionId).add(message);
    }

    public void guaranteeMessage(int connectionId, Message message) {
        setupMessageId(connectionId, message);
        setSendTime(message);
        getMessageQueue(connectionId).add(message);
    }

    private void setupMessageId(int connectionId, Message message) {
        if (message.getMessageType() != MessageType.PLAYER_CONNECTED
                && message.getMessageType() != MessageType.PLAYER_DISCONNECTED) {
            message.messageId(outgoingMessageIdCounter.get(connectionId));
            incrementOutgoingMessageIdCounter(connectionId);
        }
    }

    private void incrementOutgoingMessageIdCounter(int connectionId) {
        short counter = outgoingMessageIdCounter.get(connectionId);
        counter++;
        if (counter > HALF_SHORT_MAX_VALUE) {
            counter = 0;
            System.out.println("Reset outgoing message id counter");
        }
        outgoingMessageIdCounter.put(connectionId, counter);
    }

    private void setSendTime(Message message) {
        for (IntMap<Long> lastSentTimeMap : lastSentTimes.values()) {
            lastSentTimeMap.put(message.getMessageId(), currentTime);
        }
    }
}
