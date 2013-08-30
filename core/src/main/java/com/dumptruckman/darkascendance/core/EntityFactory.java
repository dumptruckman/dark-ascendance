package com.dumptruckman.darkascendance.core;

import com.artemis.Entity;
import com.artemis.World;
import com.dumptruckman.darkascendance.core.components.Controls;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Thrusters;
import com.dumptruckman.darkascendance.core.components.Velocity;

public class EntityFactory {

    private World world;

    EntityFactory(World world) {
        this.world = world;
    }

    public Entity createBasicShip() {
        Entity entity = world.createEntity();

        entity.addComponent(new Position());
        entity.addComponent(new Velocity());
        entity.addComponent(new Thrusters());
        entity.addComponent(new Controls());

        return entity;
    }
}
