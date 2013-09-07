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
        messageQueues.put(connectionId, new LinkedList<Message>());
        lastSentTimes.put(connectionId, new IntMap<Long>());
        outgoingMessageIdCounter.put(connectionId, (short) 0);
    }

    public void removeConnection(int connectionId) {
        messageQueues.remove(connectionId);
        lastSentTimes.remove(connectionId);
        outgoingMessageIdCounter.remove(connectionId);
    }

    public int[] getConnectionsWithRemainingMessages() {
        int[] connections = new int[messageQueues.size];
        int i = 0;
        for (IntMap.Entry<Queue<Message>> entry : messageQueues.entries()) {
            if (!entry.value.isEmpty()) {
                connections[i] = entry.key;
            }
            i++;
        }
        return connections;
    }

    public boolean hasMessagesOlderThan(int connectionId, long timeout) {
        Message message = getMessageQueue(connectionId).peek();
        if (message != null) {
            long lastTimeSent = getTimeMessageLastSent(connectionId, message.getMessageId());
            long delta = currentTime - lastTimeSent;
            //System.out.println(currentTime + " - " + lastTimeSent + " = " + delta + " for " + message);
            return delta > timeout;
        }
        return false;
    }

    private Queue<Message> getMessageQueue(int connectionId) {
        return messageQueues.get(connectionId);
    }

    private long getTimeMessageLastSent(int connectionId, short messageId) {
        return lastSentTimes.get(connectionId).get(messageId);
    }

    public Message getNextMessage(int connectionId) {
        //System.out.println("next message: " + getMessageQueue(connectionId).peek());
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
