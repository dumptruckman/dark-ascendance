package com.dumptruckman.darkascendance.shared.network;

import com.badlogic.gdx.utils.IntMap;
import com.dumptruckman.darkascendance.shared.messages.Message;

import java.util.LinkedList;
import java.util.Queue;

class MessageGuarantor {

    private static final short HALF_SHORT_MAX_VALUE = Short.MAX_VALUE / 2;

    private long currentTime = 0L;
    private short outgoingMessageIdCounter = 0;
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
        messageQueues.put(connectionId, new LinkedList<Message>());
        lastSentTimes.put(connectionId, new IntMap<Long>());
    }

    public void removeConnection(int connectionId) {
        messageQueues.remove(connectionId);
        lastSentTimes.remove(connectionId);
    }

    public IntMap.Keys getConnectionsWithRemainingMessages() {
        return messageQueues.keys();
    }

    public boolean hasMessagesOlderThan(int connectionId, int timeout) {
        Message message = getMessageQueue(connectionId).peek();
        return message != null && currentTime - getTimeMessageLastSent(connectionId, message.getMessageId()) > timeout;
    }

    private Queue<Message> getMessageQueue(int connectionId) {
        return messageQueues.get(connectionId);
    }

    private long getTimeMessageLastSent(int connectionId, short messageId) {
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

    public void guaranteeMessage(Message message) {
        setupMessageId(message);
        setSendTime(message);
    }

    private void setupMessageId(Message message) {
        message.messageId(outgoingMessageIdCounter);
        incrementOutgoingMessageIdCounter();
    }

    private void incrementOutgoingMessageIdCounter() {
        outgoingMessageIdCounter++;
        if (outgoingMessageIdCounter > HALF_SHORT_MAX_VALUE) {
            outgoingMessageIdCounter = 0;
            System.out.println("Reset outgoing message id counter");
        }
    }

    private void setSendTime(Message message) {
        for (IntMap<Long> lastSentTimeMap : lastSentTimes.values()) {
            lastSentTimeMap.put(message.getMessageId(), currentTime);
        }
    }
}
