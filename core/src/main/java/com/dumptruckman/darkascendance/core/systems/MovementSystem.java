package com.dumptruckman.darkascendance.core.systems;

import com.badlogic.gdx.math.Vector3;
import com.dumptruckman.darkascendance.core.components.Player;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Velocity;
import recs.ComponentMapper;
import recs.IntervalEntitySystem;

public class MovementSystem extends IntervalEntitySystem {

    ComponentMapper<Position> positionMap;
    ComponentMapper<Velocity> velocityMap;
    ComponentMapper<Player> playerMap;

    public MovementSystem(float interval) {
        super(interval, Position.class, Velocity.class);
    }

    @Override
    protected void processEntity(int entityId, float deltaInSec) {
        Position position = positionMap.get(entityId);
        Velocity velocity = velocityMap.get(entityId);
        Player player = playerMap.get(entityId);

        processMovement(position, velocity, player, deltaInSec);
    }

    static void processMovement(Position position, Velocity velocity, Player player, float delta) {
        position.setX(position.getX() + velocity.getX() * delta);
        position.setY(position.getY() + velocity.getY() * delta);

        if (player != null) {
            Vector3 cameraVector = player.getCamera().position;
            cameraVector.set(position.getX(), position.getY(), cameraVector.z);
        }
    }
}
