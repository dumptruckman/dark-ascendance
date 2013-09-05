package com.dumptruckman.darkascendance.shared.messages;

public class MessageBase {

    private short messageId = -1;

    public MessageBase messageId(short id) {
        this.messageId = id;
        return this;
    }

    public short getMessageId() {
        return messageId;
    }
}
