package com.dumptruckman.darkascendance.core.systems;

import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Thrust;
import com.dumptruckman.darkascendance.core.components.Velocity;
import org.junit.Test;

import static org.junit.Assert.*;

public class SystemsTest {

    @Test
    public void testThrustSystem() {
        Velocity velocity = new Velocity().setMaxX(300F).setMaxY(300F);
        Thrust thrust = new Thrust().setAccelerationAmount(50F);
        Position position = new Position();

        position.setRotation(0F);
        velocity.setX(0).setY(0);
        thrust.setThrustPercent(1F);
        ThrustSystem.processThrust(thrust, velocity, position, 1F);
        assertEquals(50F, velocity.getY(), 0.0001F);

        // TODO Extend this test
    }
}
