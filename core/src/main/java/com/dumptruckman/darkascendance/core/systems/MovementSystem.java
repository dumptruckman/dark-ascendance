package com.dumptruckman.darkascendance.core.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Velocity;

public class MovementSystem extends EntityProcessingSystem {
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Velocity> vm;

    public MovementSystem() {
            super(Aspect.getAspectForAll(Position.class, Velocity.class));
    }

    @Override
    protected void process(Entity e) {
        Position position = pm.get(e);
        Velocity velocity = vm.get(e);

        position.x += velocity.vectorX * world.delta;
        position.y += velocity.vectorY * world.delta;
    }

}
