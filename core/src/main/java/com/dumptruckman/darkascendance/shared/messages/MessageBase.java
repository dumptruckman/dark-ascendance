package com.dumptruckman.darkascendance.shared.messages;

public class MessageBase {

    private short messageId;

    public MessageBase messageId(short id) {
        this.messageId = id;
        return this;
    }

    public short getMessageId() {
        return messageId;
    }
}
