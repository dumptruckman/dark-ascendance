package com.dumptruckman.darkascendance.shared.components;

import org.junit.Test;

import static org.junit.Assert.*;

public class VelocityTest {

    @Test
    public void testReverseDirection() {
        Velocity v = new Velocity();

        v.x = 0;
        v.y = 1;
        assertEquals(180f, Velocity.getRotationRequiredToReverseVelocity(v), 0.0001f);

        v.x = 0;
        v.y = -1;
        assertEquals(0f, Velocity.getRotationRequiredToReverseVelocity(v), 0.0001f);

        v.x = 1;
        v.y = 0;
        assertEquals(90f, Velocity.getRotationRequiredToReverseVelocity(v), 0.0001f);

        v.x = -1;
        v.y = 0;
        assertEquals(270f, Velocity.getRotationRequiredToReverseVelocity(v), 0.0001f);

        v.x = 1;
        v.y = 1;
        assertEquals(135f, Velocity.getRotationRequiredToReverseVelocity(v), 0.0001f);

        v.x = -1;
        v.y = 1;
        assertEquals(225f, Velocity.getRotationRequiredToReverseVelocity(v), 0.0001f);

        v.x = 1;
        v.y = -1;
        assertEquals(45f, Velocity.getRotationRequiredToReverseVelocity(v), 0.0001f);

        v.x = -1;
        v.y = -1;
        assertEquals(315f, Velocity.getRotationRequiredToReverseVelocity(v), 0.0001f);
    }
}
