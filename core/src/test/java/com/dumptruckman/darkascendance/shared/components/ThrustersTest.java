package com.dumptruckman.darkascendance.shared.components;

import org.junit.Test;

import static org.junit.Assert.*;

public class ThrustersTest {

    @Test
    public void testThrustersBasic() {
        Thrusters thrusters = new Thrusters();
        thrusters.setAccelerationFactor(50F);

        thrusters.setThrustLevel(1F);
        assertEquals(50F, thrusters.getAccelerationAddedByThrust(), 0.0001F);

        thrusters.setThrustLevel(0F);
        assertEquals(0F, thrusters.getAccelerationAddedByThrust(), 0.0001F);

        thrusters.setThrustLevel(.5F);
        assertEquals(25F, thrusters.getAccelerationAddedByThrust(), 0.0001F);

        thrusters.setThrustLevel(-1F);
        assertEquals(-50F, thrusters.getAccelerationAddedByThrust(), 0.0001F);

        thrusters.setThrustLevel(2F);
        assertEquals(100F, thrusters.getAccelerationAddedByThrust(), 0.0001F);
    }
}
