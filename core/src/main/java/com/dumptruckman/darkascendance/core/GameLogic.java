package com.dumptruckman.darkascendance.core;

import com.artemis.World;
import com.dumptruckman.darkascendance.core.systems.AccelerationSystem;
import com.dumptruckman.darkascendance.core.systems.MovementSystem;

public class GameLogic {

    private static final float MILLIS_IN_SECOND = 1000F;

    public static final long TICK_LENGTH_MILLIS = 15L;

    private final World world;
    private final AccelerationSystem accelerationSystem;
    private final MovementSystem movementSystem;
    private final boolean doInterpolation;

    public GameLogic(World world, boolean doInterpolation) {
        this.world = world;
        this.accelerationSystem = new AccelerationSystem();
        this.movementSystem = new MovementSystem();
        this.doInterpolation = doInterpolation;
    }

    public World getWorld() {
        return world;
    }

    public AccelerationSystem getAccelerationSystem() {
        return accelerationSystem;
    }

    public MovementSystem getMovementSystem() {
        return movementSystem;
    }

    public void intializeLogicSystems() {
        world.setSystem(accelerationSystem);
        world.setSystem(movementSystem);
    }

    public void processTick(long tickDelta) {
        world.setDelta(((float) tickDelta) / MILLIS_IN_SECOND);
        world.process();
    }

    public void interpolate() {
        if (doInterpolation) {

        }
    }
}
