package com.dumptruckman.darkascendance.shared;

import com.dumptruckman.darkascendance.shared.systems.AccelerationSystem;
import com.dumptruckman.darkascendance.shared.systems.ControlsSystem;
import com.dumptruckman.darkascendance.shared.systems.MovementSystem;
import recs.EntityWorld;

import java.util.Observable;

public class GameLogic extends Observable {

    public static final float TICK_LENGTH_SECONDS = 0.015F;

    private EntityWorld world;
    private EntityFactory entityFactory;

    private ControlsSystem controlsSystem;
    private AccelerationSystem accelerationSystem;
    private MovementSystem movementSystem;

    public GameLogic(EntityWorld world, boolean createSnapshots) {
        this.world = world;

        this.entityFactory = new EntityFactory(world);

        // initialize systems
        this.controlsSystem = new ControlsSystem(createSnapshots ? TICK_LENGTH_SECONDS : 0, createSnapshots);
        this.accelerationSystem = new AccelerationSystem(createSnapshots ? TICK_LENGTH_SECONDS : 0, createSnapshots);
        this.movementSystem = new MovementSystem(createSnapshots ? TICK_LENGTH_SECONDS : 0, createSnapshots);
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
