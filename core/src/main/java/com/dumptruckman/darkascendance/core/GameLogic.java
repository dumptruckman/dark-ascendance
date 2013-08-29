package com.dumptruckman.darkascendance.core;

import com.artemis.World;
import com.dumptruckman.darkascendance.core.systems.AccelerationSystem;
import com.dumptruckman.darkascendance.core.systems.MovementSystem;
import com.dumptruckman.darkascendance.core.systems.PlayerInputSystem;

public class GameLogic {

    private final World world;
    private final AccelerationSystem accelerationSystem;
    private final MovementSystem movementSystem;

    public GameLogic(World world) {
        this.world = world;
        this.accelerationSystem = new AccelerationSystem();
        this.movementSystem = new MovementSystem();
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
}
