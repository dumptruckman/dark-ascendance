package com.dumptruckman.darkascendance.core;

import com.artemis.World;
import com.dumptruckman.darkascendance.core.graphics.TextureFactory;
import com.dumptruckman.darkascendance.core.systems.AccelerationSystem;
import com.dumptruckman.darkascendance.core.systems.MovementSystem;
import com.dumptruckman.darkascendance.util.TickRateController;

import java.util.Observable;

public class GameLogic extends Observable {

    private static final float MILLIS_IN_SECOND = 1000F;

    public static final long TICK_LENGTH_MILLIS = 15L;

    private World world;
    private boolean doInterpolation = false;
    private TickRateController tickRateController;
    private EntityFactory entityFactory;

    private AccelerationSystem accelerationSystem;
    private MovementSystem movementSystem;

    public GameLogic(World world) {
        this.world = world;
        this.tickRateController = new TickRateController(GameLogic.TICK_LENGTH_MILLIS);
        this.entityFactory = new EntityFactory(world);

        // initialize systems
        this.accelerationSystem = new AccelerationSystem();
        this.movementSystem = new MovementSystem();
    }

    protected void enableInterpolation() {
        this.doInterpolation = true;
    }

    protected World getWorld() {
        return world;
    }

    protected void addLogicSystemsAndInitializeWorld() {
        world.setSystem(accelerationSystem);
        world.setSystem(movementSystem);

        world.initialize();
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

    protected EntityFactory getEntityFactory() {
        return entityFactory;
    }
}
