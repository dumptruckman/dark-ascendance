package com.dumptruckman.darkascendance.core;

import com.dumptruckman.darkascendance.core.components.Controls;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Thrusters;
import com.dumptruckman.darkascendance.core.components.Velocity;
import recs.Entity;
import recs.EntityWorld;

public class EntityFactory {

    private EntityWorld world;

    EntityFactory(EntityWorld world) {
        this.world = world;
    }

    public Entity createBasicShip() {
        Entity entity = new Entity();
        world.addEntity(entity);

        entity.addComponent(new Position());
        entity.addComponent(new Velocity());
        entity.addComponent(new Thrusters());
        entity.addComponent(new Controls().setEntityId(entity.getId()));

        return entity;
    }
}
