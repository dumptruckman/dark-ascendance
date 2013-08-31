package com.dumptruckman.darkascendance.network.client.systems;

import com.badlogic.gdx.math.Vector3;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.network.client.components.Player;
import recs.ComponentMapper;
import recs.EntitySystem;

public class PlayerCameraSystem extends EntitySystem {

    ComponentMapper<Position> positionMap;
    ComponentMapper<Player> playerMap;

    public PlayerCameraSystem() {
        super(Player.class, Position.class);
    }

    @Override
    protected void processEntity(int entityId, float deltaSec) {
        Position position = positionMap.get(entityId);
        Player player = playerMap.get(entityId);

        Vector3 cameraVector = player.getCamera().position;
        cameraVector.set(position.getX(), position.getY(), cameraVector.z);
    }
}
