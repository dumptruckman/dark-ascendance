package com.dumptruckman.darkascendance.core.systems;

import com.badlogic.gdx.math.Vector3;
import com.dumptruckman.darkascendance.network.client.components.Player;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Velocity;
import com.dumptruckman.darkascendance.network.server.systems.SnapshotCreationSystem;
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
