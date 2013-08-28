package com.dumptruckman.darkascendance.core.components;

import org.junit.Test;

import static org.junit.Assert.*;

public class PositionTest {

    @Test
    public void testAttainRotation() throws Exception {
        Position pos = new Position();
        float changeAmount = 5;

        pos.setRotation(170);
        pos.attainRotation(180, changeAmount);
        assertEquals(175f, pos.getRotation() , 0.001);

        pos.setRotation(190);
        pos.attainRotation(180, changeAmount);
        assertEquals(185f, pos.getRotation() , 0.001);

        pos.setRotation(300);
        pos.attainRotation(5, changeAmount);
        assertEquals(305f, pos.getRotation() , 0.001);

        pos.setRotation(5);
        pos.attainRotation(300, changeAmount);
        assertEquals(0f, pos.getRotation() , 0.001);

        pos.setRotation(100);
        pos.attainRotation(103, changeAmount);
        assertEquals(103f, pos.getRotation() , 0.001);

        pos.setRotation(100);
        pos.attainRotation(97, changeAmount);
        assertEquals(97f, pos.getRotation() , 0.001);
    }
}
