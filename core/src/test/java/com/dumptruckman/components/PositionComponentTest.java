package com.dumptruckman.components;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PositionComponentTest {

    @Test
    public void testAttainRotation() throws Exception {
        PositionComponent pos = new PositionComponent();
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

    @Test
    public void testRotation360ChangesTo0() {
        PositionComponent position = new PositionComponent().setRotation(0);

        position.setRotation(360);
        assertEquals(0, position.getRotation(), 0.0001F);
    }

    @Test
    public void testRotationWrapCCW() {
        PositionComponent position = new PositionComponent().setRotation(0);

        position.setRotation(720);
        assertEquals(0, position.getRotation(), 0.0001F);

        position.setRotation(765);
        assertEquals(45, position.getRotation(), 0.0001F);
    }

    @Test
    public void testRotationWrapCW() {
        PositionComponent position = new PositionComponent().setRotation(0);

        position.setRotation(-360);
        assertEquals(0, position.getRotation(), 0.0001F);

        position.setRotation(-405);
        assertEquals(315, position.getRotation(), 0.0001F);
    }
}
