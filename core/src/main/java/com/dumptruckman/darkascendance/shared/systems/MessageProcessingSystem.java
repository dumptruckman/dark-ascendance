package com.dumptruckman.darkascendance.shared.systems;

import com.badlogic.gdx.utils.IntMap;
import com.dumptruckman.darkascendance.shared.messages.Message;
import com.dumptruckman.darkascendance.shared.network.KryoNetwork;
import com.dumptruckman.darkascendance.shared.network.MessageGuarantor;
import com.dumptruckman.darkascendance.shared.network.MessageReceiver;
import recs.EntitySystem;

import java.util.Queue;

public class MessageProcessingSystem extends EntitySystem {

    private KryoNetwork kryoNetwork;
    private MessageReceiver receiver;
    private MessageGuarantor messageGuarantor;

    public MessageProcessingSystem(KryoNetwork kryoNetwork, MessageReceiver receiver, MessageGuarantor messageGuarantor) {
        this.kryoNetwork = kryoNetwork;
        this.receiver = receiver;
        this.messageGuarantor = messageGuarantor;
    }

    @Override
    protected void processSystem(float deltaInSec) {
        updateTime(deltaInSec);
        processIncomingMessages();
        resendStaleMessagesForAllConnections();
    }

    private void updateTime(float deltaInSec) {
        long currentTime = messageGuarantor.getCurrentTime();
        currentTime += deltaInSec * 1000;
        messageGuarantor.setCurrentTime(currentTime);
    }

    private void processIncomingMessages() {
        Message message;
        while ((message = receiver.getNextIncomingMessage()) != null) {
            handleConnectOrDisconnect(message);
            kryoNetwork.handleMessage(message);
        }
    }

    private void handleConnectOrDisconnect(Message message) {
        switch (message.getMessageType()) {
            case PLAYER_CONNECTED:
                messageGuarantor.addConnection(message.getConnectionId());
                break;
            case PLAYER_DISCONNECTED:
                messageGuarantor.removeConnection(message.getConnectionId());
                break;
        }
    }

    private void resendStaleMessagesForAllConnections() {
        IntMap.Keys connectionsWithRemainingMessages = messageGuarantor.getConnectionsWithRemainingMessages();
        while (connectionsWithRemainingMessages.hasNext) {
            int connectionId = connectionsWithRemainingMessages.next();
            resendStaleMessagesForConnection(connectionId);
        }
    }

    private void resendStaleMessagesForConnection(int connectionId) {
        if (receiver.isPotentialConnection(connectionId)) {
            int timeout = receiver.getResendTimeout(connectionId);
            if (messageGuarantor.hasMessagesOlderThan(connectionId, timeout)) {
                Message message = messageGuarantor.getNextMessage(connectionId);
                short messageId = message.getMessageId();
                if (receiver.hasAcknowledgement(connectionId, messageId)) {
                    receiver.removeAcknowledgement(connectionId, messageId);
                    messageGuarantor.notifyMessageAcknowledged(connectionId, messageId);
                } else {
                    messageGuarantor.updateGuarantee(connectionId, message);
                    kryoNetwork.resendMessage(connectionId, message);
                }
            }
        }
    }

}
