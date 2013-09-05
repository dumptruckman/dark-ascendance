package com.dumptruckman.darkascendance.shared.network;

import com.dumptruckman.darkascendance.shared.messages.Acknowledgement;
import com.dumptruckman.darkascendance.shared.messages.Message;
import org.junit.Before;

public class MessageProcessingSystemTest {

    TestableKryoNetwork testKryoNetwork;
    MessageProcessingSystem testUdpSystem;

    @Before
    public void setup() {
        testKryoNetwork = new TestableKryoNetwork();
        //testUdpSystem = new MessageProcessingSystem(testKryoNetwork);
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
