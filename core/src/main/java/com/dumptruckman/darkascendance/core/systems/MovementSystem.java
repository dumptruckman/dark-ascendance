package com.dumptruckman.darkascendance.core.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.Vector3;
import com.dumptruckman.darkascendance.core.components.Player;
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

        position.setX(position.getX() + velocity.vectorX * world.delta);
        position.setY(position.getY() + velocity.vectorY * world.delta);

        Player player = e.getComponent(Player.class);
        if (player != null) {
            Vector3 camVec = player.camera.position;
            camVec.set(position.getX(), position.getY(), camVec.z);
        }
    }

}
