package com.dumptruckman.darkascendance.shared.network;

import com.dumptruckman.darkascendance.shared.messages.Message;
import com.dumptruckman.darkascendance.shared.messages.MessageType;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class MessageResequencer {

    private Map<Integer, Map<Short, Message>> messages = new ConcurrentHashMap<Integer, Map<Short, Message>>();
    ConcurrentHashMap<Integer, Short> lastOrderlyMessage = new ConcurrentHashMap<Integer, Short>();
    private Map<Integer, Message> connections = new ConcurrentHashMap<Integer, Message>();
    private Map<Integer, Message> disconnections = new ConcurrentHashMap<Integer, Message>();

    void addConnection(Integer connectionId) {
        messages.put(connectionId, new ConcurrentHashMap<Short, Message>());
        lastOrderlyMessage.put(connectionId, (short) -1);
    }

    void removeConnection(Integer connectionId) {
        messages.remove(connectionId);
        lastOrderlyMessage.remove(connectionId);
    }

    public void ensureMessageOrder(Integer connectionId, Message message) {
        if (message.getMessageType() == MessageType.PLAYER_CONNECTED) {
            System.out.println("Resquencer put player connect");
            connections.put(connectionId, message);
        } else if (message.getMessageType() == MessageType.PLAYER_DISCONNECTED) {
            System.out.println("Resquencer put player disconnect");
            disconnections.put(connectionId, message);
        } else {
            short messageId = message.getMessageId();
            if (messageId < 0) {
                throw new IllegalArgumentException("Message must have valid message ID!");
            }
            Map<Short, Message> messages = this.messages.get(connectionId);
            messages.put(message.getMessageId(), message);
        }
    }

    public boolean hasOrderlyMessage(Integer connectionId) {
        boolean hasOrderlyMessage = this.messages.get(connectionId).containsKey(getNextOrderlyMessageId(connectionId))
                || connections.containsKey(connectionId)
                || disconnections.containsKey(connectionId);
        if (!hasOrderlyMessage && !this.messages.get(connectionId).isEmpty()) {
            System.out.println("Messages out of order for connection " + connectionId);
            System.out.println(this.messages.get(connectionId).keySet());
        }
        return hasOrderlyMessage;
    }

    public Message getNextOrderlyMessage(Integer connectionId) {
        Message connectMessage = connections.remove(connectionId);
        if (connectMessage != null) {
            System.out.println("Deal with connection first");
            return connectMessage;
        }
        short nextOrderlyMessageId = getNextOrderlyMessageId(connectionId);
        Message message = this.messages.get(connectionId).get(nextOrderlyMessageId);
        if (message != null) {
            incrementLastOrderlyMessageId(connectionId);
            this.messages.get(connectionId).remove(nextOrderlyMessageId);
        } else {
            message = disconnections.remove(connectionId);
            if (message != null) {
                System.out.println("Deal with disconnection last");
            }
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
