package com.dumptruckman.darkascendance.shared.network;

import com.dumptruckman.darkascendance.shared.messages.Message;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageResequencerTest {

    private final static Message MESSAGE_1 = (Message) new Message().messageId((short) 0);
    private final static Message MESSAGE_2 = (Message) new Message().messageId((short) 1);
    private final static Message MESSAGE_3 = (Message) new Message().messageId((short) 2);
    private final static Message MESSAGE_4 = (Message) new Message().messageId((short) 3);
    private final static Message MESSAGE_5 = (Message) new Message().messageId((short) 4);
    private final static Message MESSAGE_MAX = (Message) new Message().messageId((short) (MessageGuarantor.HALF_SHORT_MAX_VALUE - 1));

    private MessageResequencer resequencer;

    @Before
    public void setup() {
        resequencer = new MessageResequencer();
        resequencer.addConnection(1);
    }

    @Test
    public void testMessagesReceivedInOrder() throws Exception {
        resequencer.ensureMessageOrder(1, MESSAGE_1);
        assertTrue(resequencer.hasOrderlyMessage(1));
        assertSame(MESSAGE_1, resequencer.getNextOrderlyMessage(1));
        resequencer.ensureMessageOrder(1, MESSAGE_2);
        assertTrue(resequencer.hasOrderlyMessage(1));
        assertSame(MESSAGE_2, resequencer.getNextOrderlyMessage(1));
        resequencer.ensureMessageOrder(1, MESSAGE_3);
        resequencer.ensureMessageOrder(1, MESSAGE_4);
        resequencer.ensureMessageOrder(1, MESSAGE_5);
        assertTrue(resequencer.hasOrderlyMessage(1));
        assertSame(MESSAGE_3, resequencer.getNextOrderlyMessage(1));
        assertTrue(resequencer.hasOrderlyMessage(1));
        assertSame(MESSAGE_4, resequencer.getNextOrderlyMessage(1));
        assertTrue(resequencer.hasOrderlyMessage(1));
        assertSame(MESSAGE_5, resequencer.getNextOrderlyMessage(1));
    }

    @Test
    public void testMessagesReceivedOutOfOrder() throws Exception {
        resequencer.ensureMessageOrder(1, MESSAGE_2);
        assertFalse(resequencer.hasOrderlyMessage(1));
        assertNull(resequencer.getNextOrderlyMessage(1));
        resequencer.ensureMessageOrder(1, MESSAGE_3);
        assertFalse(resequencer.hasOrderlyMessage(1));
        assertNull(resequencer.getNextOrderlyMessage(1));
        resequencer.ensureMessageOrder(1, MESSAGE_5);
        assertFalse(resequencer.hasOrderlyMessage(1));
        assertNull(resequencer.getNextOrderlyMessage(1));
        resequencer.ensureMessageOrder(1, MESSAGE_1);
        assertTrue(resequencer.hasOrderlyMessage(1));
        assertSame(MESSAGE_1, resequencer.getNextOrderlyMessage(1));
        assertTrue(resequencer.hasOrderlyMessage(1));
        assertSame(MESSAGE_2, resequencer.getNextOrderlyMessage(1));
        assertTrue(resequencer.hasOrderlyMessage(1));
        assertSame(MESSAGE_3, resequencer.getNextOrderlyMessage(1));
        assertFalse(resequencer.hasOrderlyMessage(1));
        resequencer.ensureMessageOrder(1, MESSAGE_4);
        assertTrue(resequencer.hasOrderlyMessage(1));
        assertSame(MESSAGE_4, resequencer.getNextOrderlyMessage(1));
        assertTrue(resequencer.hasOrderlyMessage(1));
        assertSame(MESSAGE_5, resequencer.getNextOrderlyMessage(1));
    }

    @Test
    public void testMessageReceivedInOrderAtCap() throws Exception {
        resequencer.lastOrderlyMessage.put(1, (short) (MessageGuarantor.HALF_SHORT_MAX_VALUE - 2));
        resequencer.ensureMessageOrder(1, MESSAGE_MAX);
        assertTrue(resequencer.hasOrderlyMessage(1));
        resequencer.ensureMessageOrder(1, MESSAGE_1);
        assertTrue(resequencer.hasOrderlyMessage(1));
        assertSame(MESSAGE_MAX, resequencer.getNextOrderlyMessage(1));
        assertSame(MESSAGE_1, resequencer.getNextOrderlyMessage(1));
    }

    @Test
    public void testMessageReceivedOutOfOrderAtCap() throws Exception {
        resequencer.lastOrderlyMessage.put(1, (short) (MessageGuarantor.HALF_SHORT_MAX_VALUE - 2));
        resequencer.ensureMessageOrder(1, MESSAGE_1);
        assertFalse(resequencer.hasOrderlyMessage(1));
        assertNull(resequencer.getNextOrderlyMessage(1));
        resequencer.ensureMessageOrder(1, MESSAGE_MAX);
        assertTrue(resequencer.hasOrderlyMessage(1));
        assertSame(MESSAGE_MAX, resequencer.getNextOrderlyMessage(1));
        assertSame(MESSAGE_1, resequencer.getNextOrderlyMessage(1));
    }
}
