package com.dumptruckman.darkascendance.shared.systems;

import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.shared.messages.Acknowledgement;
import com.dumptruckman.darkascendance.shared.messages.AcknowledgementBatch;
import com.dumptruckman.darkascendance.shared.messages.Message;
import com.dumptruckman.darkascendance.shared.messages.MessageFactory;
import com.dumptruckman.darkascendance.shared.network.KryoNetwork;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UdpMessageGuarantorSystemTest {

    private static class TestKryoNetwork extends KryoNetwork {

        AcknowledgementBatch lastAckSent;
        int lastAckConnection;

        @Override
        protected void sendMessage(final Message message) { }

        @Override
        public void resendMessage(final int connectionId, final Message message) { }

        @Override
        public void sendAcknowledgement(final int connectionId, final AcknowledgementBatch acknowledgementBatch) {
            lastAckSent = acknowledgementBatch;
            lastAckConnection = connectionId;
        }

        @Override
        public void handleMessage(final Message message) { }

        public AcknowledgementBatch getLastAcknowledgementSent() {
            return lastAckSent;
        }

        public int getLastAcknowledgementConnectionId() {
            return lastAckConnection;
        }
    }

    TestKryoNetwork testKryoNetwork;
    UdpMessageGuarantorSystem testUdpSystem;

    @Before
    public void setup() {
        testKryoNetwork = new TestKryoNetwork();
        testUdpSystem = new UdpMessageGuarantorSystem(testKryoNetwork);
    }

    @Test
    public void testReceiveMessageFromUnknownConnectionNoPlayersConnected() throws Exception {
        testUdpSystem.receiveMessage(0, MessageFactory.playerInputState(new Controls()), 0);
        assertNull(testUdpSystem.getAcknowledgementBatch(0));
    }

    @Test
    public void testReceiveDisconnectFromUnknownConnection() throws Exception {
        testUdpSystem.receiveMessage(0, MessageFactory.playerDisconnected(0), 0);
        assertNull(testUdpSystem.getAcknowledgementBatch(0));
        testUdpSystem.processSystem(0.0005f);
        assertNull(testKryoNetwork.getLastAcknowledgementSent());
    }

    @Test
    public void testReceiveAcknowledgmentAfterConnectButBeforeProcess() throws Exception {
        testUdpSystem.receiveMessage(1, MessageFactory.playerConnected(1), 0);
        assertNotNull(testUdpSystem.getAcknowledgementBatch(1));
        assertEquals(0, testUdpSystem.getAcknowledgementBatch(1).size());
        testUdpSystem.receiveMessage(1, MessageFactory.playerInputState(new Controls()), 0);
        assertEquals(1, testUdpSystem.getAcknowledgementBatch(1).size());
        testUdpSystem.processSystem(0.0005f);
        assertEquals(1, testKryoNetwork.getLastAcknowledgementConnectionId());
    }

    @Test
    public void testReceiveMessageFromUnknownConnectionWithPlayersConnected() throws Exception {
        testUdpSystem.receiveMessage(5, MessageFactory.playerConnected(5), 0);
        testUdpSystem.processSystem(0.0005f);
        assertNull(testKryoNetwork.getLastAcknowledgementSent());

        testUdpSystem.receiveMessage(0, MessageFactory.playerInputState(new Controls()), 0);
        testUdpSystem.processSystem(0.0005f);
        assertNull(testKryoNetwork.getLastAcknowledgementSent());

        testUdpSystem.receiveMessage(5, MessageFactory.playerInputState(new Controls()), 0);
        testUdpSystem.processSystem(0.0005f);
        assertEquals(5, testKryoNetwork.getLastAcknowledgementConnectionId());
    }

    @Test
    public void testReceiveDisconnectFromUnknownConnectionWithPlayersConnected() throws Exception {
        testUdpSystem.receiveMessage(5, MessageFactory.playerConnected(5), 0);
        testUdpSystem.processSystem(0.0005f);
        assertNull(testKryoNetwork.getLastAcknowledgementSent());

        testUdpSystem.receiveMessage(0, MessageFactory.playerDisconnected(0), 0);
        testUdpSystem.processSystem(0.0005f);
        assertNull(testKryoNetwork.getLastAcknowledgementSent());

        testUdpSystem.receiveMessage(5, MessageFactory.playerInputState(new Controls()), 0);
        assertNotNull(testUdpSystem.getAcknowledgementBatch(5));
        assertEquals(1, testUdpSystem.getAcknowledgementBatch(5).size());
        testUdpSystem.processSystem(0.0005f);
        assertEquals(5, testKryoNetwork.getLastAcknowledgementConnectionId());
    }


}
