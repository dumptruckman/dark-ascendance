package com.dumptruckman.darkascendance.shared.network;

import com.dumptruckman.darkascendance.shared.messages.Acknowledgement;
import com.dumptruckman.darkascendance.shared.messages.Message;

public class TestableKryoNetwork extends KryoNetwork {

    Acknowledgement lastAckSent;
    int lastAckConnection;

    @Override
    protected void sendMessage(final Message message) { }

    @Override
    public void resendMessage(final int connectionId, final Message message) { }

    @Override
    public void sendAcknowledgement(final int connectionId, final Acknowledgement acknowledgement) {
        lastAckSent = acknowledgement;
        lastAckConnection = connectionId;
    }

    @Override
    public void handleMessage(final Message message) { }

    public Acknowledgement getLastAcknowledgementSent() {
        return lastAckSent;
    }

    public int getLastAcknowledgementConnectionId() {
        return lastAckConnection;
    }
}
