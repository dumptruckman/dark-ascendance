package com.dumptruckman.darkascendance.shared.messages;

public class Message extends MessageBase {

    private MessageType messageType;
    private int connectionId;
    private boolean forAllConnections = true;
    private boolean forAllButOneConnections = false;
    private boolean important = false;
    private long messageTime;

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

    public Message onlyForConnectionId(int id) {
        this.connectionId = id;
        this.forAllConnections = false;
        this.forAllButOneConnections = false;
        return this;
    }

    public Message important(boolean important) {
        this.important = important;
        return this;
    }

    public boolean isImportant() {
        return important;
    }

    public Message notForConnectionId(int id) {
        this.connectionId = id;
        this.forAllConnections = true;
        this.forAllButOneConnections = true;
        return this;
    }

    public boolean isForAllConnections() {
        return forAllConnections;
    }

    public boolean isForAllButOneConnections() {
        return forAllButOneConnections;
    }

    public long getTime() {
        return messageTime;
    }

    public Message time(long messageTime) {
        this.messageTime = messageTime;
        return this;
    }
}
