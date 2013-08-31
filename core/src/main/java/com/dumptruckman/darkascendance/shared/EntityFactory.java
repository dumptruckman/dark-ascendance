package com.dumptruckman.darkascendance.shared;

import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.shared.components.Position;
import com.dumptruckman.darkascendance.shared.components.Thrusters;
import com.dumptruckman.darkascendance.shared.components.Velocity;
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
        entity.addComponent(new Controls());

        return entity;
    }
}
