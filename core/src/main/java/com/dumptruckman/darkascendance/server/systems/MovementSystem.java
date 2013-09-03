package com.dumptruckman.darkascendance.server.systems;

import com.dumptruckman.darkascendance.shared.components.Position;
import com.dumptruckman.darkascendance.shared.components.Velocity;
import recs.ComponentMapper;
import recs.IntervalEntitySystem;

public class MovementSystem extends IntervalEntitySystem {

    ComponentMapper<Position> positionMap;
    ComponentMapper<Velocity> velocityMap;


    public MovementSystem(float interval) {
        super(interval, Position.class, Velocity.class);
    }

    @Override
    protected void processEntity(int entityId, float deltaInSec) {
        Position position = positionMap.get(entityId);
        Velocity velocity = velocityMap.get(entityId);

        processMovement(position, velocity, deltaInSec);
    }

    static void processMovement(Position position, Velocity velocity, float delta) {
        float xDelta = velocity.getX() * delta;
        position.setX(position.getX() + xDelta);
        float yDelta = velocity.getY() * delta;
        position.setY(position.getY() + yDelta);

        if (xDelta != 0F || yDelta != 0F) {
            SnapshotCreationSystem.addChangedComponentToSnapshot(position);
        }
    }
}