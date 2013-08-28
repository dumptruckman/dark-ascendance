package com.dumptruckman.darkascendance.core.systems;

import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Thrust;
import com.dumptruckman.darkascendance.core.components.Velocity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ThrustSystemTest {

    Velocity velocity;
    Thrust thrust;
    Position position;

    @Before
    public void setUp() {
        velocity = new Velocity().setMaxX(300F).setMaxY(300F);
        thrust = new Thrust().setAccelerationAmount(50F).setThrustPercent(1F);
        position = new Position();
    }

    @Test
    public void testThrustUp() {
        position.setRotation(0F);
        velocity.setX(0).setY(0);
        ThrustSystem.processThrust(thrust, velocity, position, 1F);
        assertEquals(50F, velocity.getY(), 0.0001F);
        assertEquals(0F, velocity.getX(), 0.0001F);
    }

    @Test
    public void testThrustDown() {
        position.setRotation(180F);
        velocity.setX(0).setY(0);
        ThrustSystem.processThrust(thrust, velocity, position, 1F);
        assertEquals(-50F, velocity.getY(), 0.0001F);
        assertEquals(0F, velocity.getX(), 0.0001F);
    }

    @Test
    public void testThrustRight() {
        velocity.setX(0).setY(0);
        position.setRotation(270F);
        ThrustSystem.processThrust(thrust, velocity, position, 1F);
        assertEquals(50F, velocity.getX(), 0.0001F);
        assertEquals(0F, velocity.getY(), 0.0001F);
    }

    @Test
    public void testThrustLeft() {
        velocity.setX(0).setY(0);
        position.setRotation(90F);
        ThrustSystem.processThrust(thrust, velocity, position, 1F);
        assertEquals(-50F, velocity.getX(), 0.0001F);
        assertEquals(0F, velocity.getY(), 0.0001F);
    }

    @Test
    public void testThrustUpRight() {
        velocity.setX(0).setY(0);
        position.setRotation(315F);
        ThrustSystem.processThrust(thrust, velocity, position, 1F);
        assertEquals(35.3F, velocity.getX(), 0.1F);
        assertEquals(35.3F, velocity.getY(), 0.1F);
    }

    @Test
    public void testThrustDownRight() {
        velocity.setX(0).setY(0);
        position.setRotation(225F);
        ThrustSystem.processThrust(thrust, velocity, position, 1F);
        assertEquals(35.3F, velocity.getX(), 0.1F);
        assertEquals(-35.3F, velocity.getY(), 0.1F);
    }

    @Test
    public void testThrustDownLeft() {
        velocity.setX(0).setY(0);
        position.setRotation(135F);
        ThrustSystem.processThrust(thrust, velocity, position, 1F);
        assertEquals(-35.3F, velocity.getX(), 0.1F);
        assertEquals(-35.3F, velocity.getY(), 0.1F);
    }

    @Test
    public void testThrustUpLeft() {
        velocity.setX(0).setY(0);
        position.setRotation(45F);
        ThrustSystem.processThrust(thrust, velocity, position, 1F);
        assertEquals(-35.3F, velocity.getX(), 0.1F);
        assertEquals(35.3F, velocity.getY(), 0.1F);
    }

    @Test
    public void testThrustUpHalfDelta() {
        position.setRotation(0F);
        velocity.setX(0).setY(0);
        ThrustSystem.processThrust(thrust, velocity, position, .5F);
        assertEquals(25F, velocity.getY(), 0.0001F);
        assertEquals(0F, velocity.getX(), 0.0001F);
    }

    @Test
    public void testThrustUpHalfThrust() {
        position.setRotation(0F);
        velocity.setX(0).setY(0);
        thrust.setThrustPercent(.5F);
        ThrustSystem.processThrust(thrust, velocity, position, 1F);
        assertEquals(25F, velocity.getY(), 0.0001F);
        assertEquals(0F, velocity.getX(), 0.0001F);
    }

    @Test
    public void testThrustUpHalfThrustHalfDelta() {
        position.setRotation(0F);
        velocity.setX(0).setY(0);
        thrust.setAccelerationAmount(40).setThrustPercent(.5F);
        ThrustSystem.processThrust(thrust, velocity, position, .5F);
        assertEquals(10F, velocity.getY(), 0.0001F);
        assertEquals(0F, velocity.getX(), 0.0001F);
    }
}
