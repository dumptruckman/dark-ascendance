package com.dumptruckman.darkascendance.shared.messages;

public class MessageBase {

    public static final short INVALID_MESSAGE_ID = -1;

    private short messageId = INVALID_MESSAGE_ID;

    public MessageBase messageId(short id) {
        this.messageId = id;
        return this;
    }

    public short getMessageId() {
        return messageId;
    }
}
