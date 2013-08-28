package com.dumptruckman.darkascendance.core.systems;

import com.badlogic.gdx.math.MathUtils;
import com.dumptruckman.darkascendance.core.components.Thrusters;
import com.dumptruckman.darkascendance.core.components.Velocity;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AccelerationSystemTest {

    Velocity velocity;
    Thrusters thrusters;
    float angle;

    @Before
    public void setUp() {
        velocity = new Velocity().setMaxX(300F).setMaxY(300F);
        thrusters = new Thrusters().setAccelerationFactor(50F).setThrustLevel(1F);
        angle = 0F;
    }

    @Test
    public void testThrustUp() {
        angle = 0F;
        velocity.setX(0).setY(0);
        AccelerationSystem.addForwardAccelerationToVelocity(thrusters.getAccelerationAddedByThrust(), velocity, angle);
        assertEquals(50F, velocity.getY(), 0.0001F);
        assertEquals(0F, velocity.getX(), 0.0001F);
    }

    @Test
    public void testThrustDown() {
        angle = MathUtils.PI;
        velocity.setX(0).setY(0);
        AccelerationSystem.addForwardAccelerationToVelocity(thrusters.getAccelerationAddedByThrust(), velocity, angle);        assertEquals(-50F, velocity.getY(), 0.0001F);
        assertEquals(0F, velocity.getX(), 0.0001F);
    }

    @Test
    public void testThrustRight() {
        velocity.setX(0).setY(0);
        angle = MathUtils.PI + MathUtils.PI / 2;
        AccelerationSystem.addForwardAccelerationToVelocity(thrusters.getAccelerationAddedByThrust(), velocity, angle);
        assertEquals(50F, velocity.getX(), 0.0001F);
        assertEquals(0F, velocity.getY(), 0.0001F);
    }

    @Test
    public void testThrustLeft() {
        velocity.setX(0).setY(0);
        angle = MathUtils.PI / 2;
        AccelerationSystem.addForwardAccelerationToVelocity(thrusters.getAccelerationAddedByThrust(), velocity, angle);
        assertEquals(-50F, velocity.getX(), 0.0001F);
        assertEquals(0F, velocity.getY(), 0.0001F);
    }

    @Test
    public void testThrustUpRight() {
        velocity.setX(0).setY(0);
        angle = MathUtils.PI + 3 * MathUtils.PI / 4;
        AccelerationSystem.addForwardAccelerationToVelocity(thrusters.getAccelerationAddedByThrust(), velocity, angle);
        assertEquals(35.3F, velocity.getX(), 0.1F);
        assertEquals(35.3F, velocity.getY(), 0.1F);
    }

    @Test
    public void testThrustDownRight() {
        velocity.setX(0).setY(0);
        angle = MathUtils.PI + MathUtils.PI / 4;
        AccelerationSystem.addForwardAccelerationToVelocity(thrusters.getAccelerationAddedByThrust(), velocity, angle);
        assertEquals(35.3F, velocity.getX(), 0.1F);
        assertEquals(-35.3F, velocity.getY(), 0.1F);
    }

    @Test
    public void testThrustDownLeft() {
        velocity.setX(0).setY(0);
        angle = MathUtils.PI - MathUtils.PI / 4;
        AccelerationSystem.addForwardAccelerationToVelocity(thrusters.getAccelerationAddedByThrust(), velocity, angle);
        assertEquals(-35.3F, velocity.getX(), 0.1F);
        assertEquals(-35.3F, velocity.getY(), 0.1F);
    }

    @Test
    public void testThrustUpLeft() {
        velocity.setX(0).setY(0);
        angle = MathUtils.PI / 4;
        AccelerationSystem.addForwardAccelerationToVelocity(thrusters.getAccelerationAddedByThrust(), velocity, angle);
        assertEquals(-35.3F, velocity.getX(), 0.1F);
        assertEquals(35.3F, velocity.getY(), 0.1F);
    }

    @Test
    public void testThrustUpHalfThrust() {
        angle = 0F;
        velocity.setX(0).setY(0);
        thrusters.setThrustLevel(.5F);
        AccelerationSystem.addForwardAccelerationToVelocity(thrusters.getAccelerationAddedByThrust(), velocity, angle);
        assertEquals(25F, velocity.getY(), 0.0001F);
        assertEquals(0F, velocity.getX(), 0.0001F);
    }

    @Test
    public void testZeroThrust() {
        angle = 0F;
        velocity.setX(0).setY(0);
        thrusters.setThrustLevel(0F);
        AccelerationSystem.addForwardAccelerationToVelocity(thrusters.getAccelerationAddedByThrust(), velocity, angle);
        assertEquals(0F, velocity.getY(), 0.0001F);
        assertEquals(0F, velocity.getX(), 0.0001F);
    }

    @Test
    public void testDeltaModification() {
        thrusters.setThrustLevel(1F);
        float deltaAcceleration = AccelerationSystem.modifyAccelerationByDelta(thrusters.getAccelerationAddedByThrust(), .5F);
        assertEquals(25F, deltaAcceleration, 0.0001F);
    }

    @Test
    public void testGetThrustersAcceleration() {
        float accel = AccelerationSystem.getThrustersAcceleration(thrusters);
        assertEquals(50F, accel, 0.0001F);
        accel = AccelerationSystem.getThrustersAcceleration(null);
        assertEquals(0F, accel, 0.0001F);
    }
}
