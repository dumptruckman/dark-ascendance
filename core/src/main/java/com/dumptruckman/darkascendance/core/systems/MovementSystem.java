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
    @Mapper
    ComponentMapper<Position> positionMap;
    @Mapper
    ComponentMapper<Velocity> velocityMap;

    public MovementSystem() {
        super(Aspect.getAspectForAll(Position.class, Velocity.class));
    }

    @Override
    protected void process(Entity entity) {
        Position position = positionMap.get(entity);
        Velocity velocity = velocityMap.get(entity);

        position.setX(position.getX() + velocity.getX() * world.getDelta());
        position.setY(position.getY() + velocity.getY() * world.getDelta());

        Player player = entity.getComponent(Player.class);
        if (player != null) {
            Vector3 cameraVector = player.getCamera().position;
            cameraVector.set(position.getX(), position.getY(), cameraVector.z);
        }
    }

}
