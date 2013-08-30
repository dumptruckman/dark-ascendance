package com.dumptruckman.darkascendance.network.messages;

public class Message {

    private MessageType messageType;
    private int connectionId;

    public Message type(MessageType messageType) {
        this.messageType = messageType;
        return this;
    }

    public Message connectionId(int id) {
        this.connectionId = id;
        return this;
    }

    public int getConnectionId() {
        return connectionId;
    }

    public MessageType getMessageType() {
        return messageType;
    }
}
