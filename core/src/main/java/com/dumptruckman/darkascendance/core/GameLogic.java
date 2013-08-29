package com.dumptruckman.darkascendance.core;

import com.artemis.World;
import com.dumptruckman.darkascendance.core.systems.AccelerationSystem;
import com.dumptruckman.darkascendance.core.systems.MovementSystem;
import com.dumptruckman.darkascendance.util.TickRateController;

import java.util.Observable;

public class GameLogic extends Observable {

    private static final float MILLIS_IN_SECOND = 1000F;

    public static final long TICK_LENGTH_MILLIS = 15L;

    private final World world;
    private final AccelerationSystem accelerationSystem;
    private final MovementSystem movementSystem;
    private final boolean doInterpolation;
    private final TickRateController tickRateController;

    public GameLogic(World world, boolean doInterpolation) {
        this.world = world;
        this.accelerationSystem = new AccelerationSystem();
        this.movementSystem = new MovementSystem();
        this.doInterpolation = doInterpolation;
        this.tickRateController = new TickRateController(GameLogic.TICK_LENGTH_MILLIS);
    }

    protected World getWorld() {
        return world;
    }

    protected AccelerationSystem getAccelerationSystem() {
        return accelerationSystem;
    }

    protected MovementSystem getMovementSystem() {
        return movementSystem;
    }

    protected void intializeLogicSystems() {
        world.setSystem(accelerationSystem);
        world.setSystem(movementSystem);
    }

    public void processTick(long tickDelta) {
        world.setDelta(((float) tickDelta) / MILLIS_IN_SECOND);
        world.process();
    }

    protected long getTickRateDelta() {
        return tickRateController.getDelta();
    }

    protected void waitForTickIfNecessary() {
        try {
            tickRateController.waitForTickIfNecessary();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    protected boolean hasTickElapsed() {
        return tickRateController.hasTickElapsed();
    }

    protected void prepareForNextTick() {
        tickRateController.prepareForNextTick();
    }

    protected void interpolate() {
        if (doInterpolation) {

        }
    }
}
