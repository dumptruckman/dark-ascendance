package com.dumptruckman.darkascendance.core;

import com.artemis.World;
import com.dumptruckman.darkascendance.core.systems.AccelerationSystem;
import com.dumptruckman.darkascendance.core.systems.ControlsSystem;
import com.dumptruckman.darkascendance.core.systems.MovementSystem;

import java.util.Observable;

public class GameLogic extends Observable {

    public static final float TICK_LENGTH_SECONDS = 0.015F;

    private World world;
    private boolean doInterpolation = false;
    private EntityFactory entityFactory;

    private ControlsSystem controlsSystem;
    private AccelerationSystem accelerationSystem;
    private MovementSystem movementSystem;

    public GameLogic(World world) {
        this.world = world;
        this.entityFactory = new EntityFactory(world);

        // initialize systems
        this.controlsSystem = new ControlsSystem(TICK_LENGTH_SECONDS);
        this.accelerationSystem = new AccelerationSystem(TICK_LENGTH_SECONDS);
        this.movementSystem = new MovementSystem(TICK_LENGTH_SECONDS);
    }

    protected void enableInterpolation() {
        this.doInterpolation = true;
    }

    protected World getWorld() {
        return world;
    }

    protected void addLogicSystemsAndInitializeWorld() {
        world.setSystem(controlsSystem);
        world.setSystem(accelerationSystem);
        world.setSystem(movementSystem);

        world.initialize();
    }

    public void processGameLogic(float delta) {
        world.setDelta(delta);
        world.process();
    }

    protected void interpolate() {
        if (doInterpolation) {

        }
    }

    protected EntityFactory getEntityFactory() {
        return entityFactory;
    }
}
