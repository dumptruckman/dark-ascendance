package com.dumptruckman.darkascendance.core;

import com.dumptruckman.darkascendance.core.systems.AccelerationSystem;
import com.dumptruckman.darkascendance.core.systems.ControlsSystem;
import com.dumptruckman.darkascendance.core.systems.MovementSystem;
import recs.EntityWorld;

import java.util.Observable;

public class GameLogic extends Observable {

    public static final float TICK_LENGTH_SECONDS = 0.015F;

    private EntityWorld world;
    private boolean doInterpolation = false;
    private EntityFactory entityFactory;

    private ControlsSystem controlsSystem;
    private AccelerationSystem accelerationSystem;
    private MovementSystem movementSystem;

    public GameLogic(EntityWorld world) {
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

    protected EntityWorld getWorld() {
        return world;
    }

    protected void addLogicSystems() {
        world.addSystem(controlsSystem, accelerationSystem, movementSystem);
    }

    public void processGameLogic(float delta) {
        world.process(delta);
    }

    protected EntityFactory getEntityFactory() {
        return entityFactory;
    }
}
