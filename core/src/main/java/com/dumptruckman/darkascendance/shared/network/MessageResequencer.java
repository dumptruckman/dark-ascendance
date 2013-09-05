package com.dumptruckman.darkascendance.shared.network;

import com.dumptruckman.darkascendance.shared.messages.Message;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class MessageResequencer {

    private Map<Integer, Map<Short, Message>> messages = new ConcurrentHashMap<Integer, Map<Short, Message>>();
    ConcurrentHashMap<Integer, Short> lastOrderlyMessage = new ConcurrentHashMap<Integer, Short>();

    void addConnection(Integer connectionId) {
        messages.put(connectionId, new ConcurrentHashMap<Short, Message>());
        lastOrderlyMessage.put(connectionId, (short) -1);
    }

    void removeConnection(Integer connectionId) {
        messages.remove(connectionId);
        lastOrderlyMessage.remove(connectionId);
    }

    public void ensureMessageOrder(Integer connectionId, Message message) {
        short messageId = message.getMessageId();
        if (messageId < 0) {
            throw new IllegalArgumentException("Message must have valid message ID!");
        }
        Map<Short, Message> messages = this.messages.get(connectionId);
        messages.put(message.getMessageId(), message);
    }

    public boolean hasOrderlyMessage(Integer connectionId) {
        boolean hasOrderlyMessage = this.messages.get(connectionId).containsKey(getNextOrderlyMessageId(connectionId));
        if (!hasOrderlyMessage) {
            System.out.println("Some messages have been received out of order for " + connectionId);
        }
        return hasOrderlyMessage;
    }

    public Message getNextOrderlyMessage(Integer connectionId) {
        short nextOrderlyMessageId = getNextOrderlyMessageId(connectionId);
        Message message = this.messages.get(connectionId).get(nextOrderlyMessageId);
        if (message != null) {
            incrementLastOrderlyMessageId(connectionId);
            this.messages.get(connectionId).remove(nextOrderlyMessageId);
        }
        return message;
    }

    short getNextOrderlyMessageId(Integer connectionId) {
        short nextOrderlyMessage = (short) (this.lastOrderlyMessage.get(connectionId) + 1);
        if (nextOrderlyMessage == MessageGuarantor.HALF_SHORT_MAX_VALUE) {
            nextOrderlyMessage = 0;
        }
        return nextOrderlyMessage;
    }

    private void incrementLastOrderlyMessageId(Integer connectionId) {
        short nextOrderlyMessage = (short) (this.lastOrderlyMessage.get(connectionId) + 1);
        if (nextOrderlyMessage == MessageGuarantor.HALF_SHORT_MAX_VALUE) {
            nextOrderlyMessage = -1;
        }
        this.lastOrderlyMessage.put(connectionId, nextOrderlyMessage);
    }
}
