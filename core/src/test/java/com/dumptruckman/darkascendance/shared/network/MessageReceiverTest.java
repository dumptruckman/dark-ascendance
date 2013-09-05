package com.dumptruckman.darkascendance.shared.network;

import com.dumptruckman.darkascendance.shared.messages.Message;
import com.dumptruckman.darkascendance.shared.messages.MessageType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageReceiverTest {

    final static Message MESSAGE_1 = (Message) new Message().type(MessageType.PLAYER_INPUT_STATE).important(true).messageId((short) 0);
    final static Message MESSAGE_2 = (Message) new Message().type(MessageType.PLAYER_INPUT_STATE).important(true).messageId((short) 1);
    final static Message MESSAGE_3 = (Message) new Message().type(MessageType.PLAYER_INPUT_STATE).important(true).messageId((short) 2);

    MessageReceiver receiver;

    @Before
    public void setup() throws Exception {
        receiver = new MessageReceiver(new TestableKryoNetwork());
        receiver.addConnection(1);
    }

    @Test
    public void testReceiveMessagesOutOfOrder() throws Exception {
        receiver.receiveMessage(1, MESSAGE_2, 0);
        receiver.receiveMessage(1, MESSAGE_1, 0);
        receiver.receiveMessage(1, MESSAGE_3, 0);
        assertSame(MESSAGE_1, receiver.getNextIncomingMessage());
        assertSame(MESSAGE_2, receiver.getNextIncomingMessage());
        assertSame(MESSAGE_3, receiver.getNextIncomingMessage());
    }
}
