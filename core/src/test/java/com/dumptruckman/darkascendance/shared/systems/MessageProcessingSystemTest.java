package com.dumptruckman.darkascendance.shared.systems;

import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.shared.messages.Acknowledgement;
import com.dumptruckman.darkascendance.shared.messages.Message;
import com.dumptruckman.darkascendance.shared.messages.MessageFactory;
import com.dumptruckman.darkascendance.shared.network.KryoNetwork;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageProcessingSystemTest {

    private static class TestKryoNetwork extends KryoNetwork {

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

    TestKryoNetwork testKryoNetwork;
    MessageProcessingSystem testUdpSystem;

    @Before
    public void setup() {
        testKryoNetwork = new TestKryoNetwork();
        testUdpSystem = new MessageProcessingSystem(testKryoNetwork);
    }

    /*
    @Test
    public void testReceiveMessageFromUnknownConnectionNoPlayersConnected() throws Exception {
        testUdpSystem.receiveMessage(0, MessageFactory.playerInputState(new Controls()), 0);
        assertNull(testKryoNetwork.getLastAcknowledgementSent());
    }

    @Test
    public void testReceiveDisconnectFromUnknownConnection() throws Exception {
        testUdpSystem.receiveMessage(0, MessageFactory.playerDisconnected(0), 0);
        assertNull(testKryoNetwork.getLastAcknowledgementSent());
    }

    @Test
    public void testReceiveMessageFromUnknownConnectionWithPlayersConnected() throws Exception {
        testUdpSystem.receiveMessage(5, MessageFactory.playerConnected(5), 0);
        assertNull(testKryoNetwork.getLastAcknowledgementSent());

        testUdpSystem.receiveMessage(0, MessageFactory.playerInputState(new Controls()), 0);
        assertNull(testKryoNetwork.getLastAcknowledgementSent());

        testUdpSystem.receiveMessage(5, MessageFactory.playerInputState(new Controls()), 0);
        assertEquals(5, testKryoNetwork.getLastAcknowledgementConnectionId());
    }

    @Test
    public void testReceiveDisconnectFromUnknownConnectionWithPlayersConnected() throws Exception {
        testUdpSystem.receiveMessage(5, MessageFactory.playerConnected(5), 0);
        assertNull(testKryoNetwork.getLastAcknowledgementSent());

        testUdpSystem.receiveMessage(0, MessageFactory.playerDisconnected(0), 0);
        assertNull(testKryoNetwork.getLastAcknowledgementSent());

        testUdpSystem.receiveMessage(5, MessageFactory.playerInputState(new Controls()), 0);
        assertNotNull(testKryoNetwork.getLastAcknowledgementSent());
        assertEquals(5, testKryoNetwork.getLastAcknowledgementConnectionId());
    }

*/

}
