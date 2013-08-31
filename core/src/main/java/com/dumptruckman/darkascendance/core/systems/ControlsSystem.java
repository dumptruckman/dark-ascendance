package com.dumptruckman.darkascendance.core.systems;

import com.dumptruckman.darkascendance.core.components.Controls;
import com.dumptruckman.darkascendance.core.components.Player;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Thrusters;
import com.dumptruckman.darkascendance.core.components.Velocity;
import recs.ComponentMapper;
import recs.Entity;
import recs.IntervalEntitySystem;

public class ControlsSystem extends IntervalEntitySystem {

    private static final float ROTATION_SPEED = 80F;

    ComponentMapper<Controls> controlsMap;
    ComponentMapper<Position> positionMap;
    ComponentMapper<Velocity> velocityMap;

    private float timeToFire;

    public ControlsSystem(float interval) {
        super(interval, Controls.class, Position.class, Player.class, Velocity.class);
    }

    @Override
    protected void processEntity(int entityId, float deltaInSec) {
        Controls controls = controlsMap.get(entityId);
        Position position = positionMap.get(entityId);
        Velocity velocity = velocityMap.get(entityId);
        Entity entity = world.getEntity(entityId);
        Thrusters thrusters = entity.getComponent(Thrusters.class);

        if (thrusters != null) {
            if(controls.up) {
                thrusters.setThrustLevel(1F);
            } else {
                thrusters.setThrustLevel(0F);
            }
        }
        if(controls.down) {
            position.attainRotation(Velocity.getRotationRequiredToReverseVelocity(velocity), (deltaInSec * ROTATION_SPEED));
        }
        if(controls.left) {
            position.setRotation(position.getRotation() + (deltaInSec * ROTATION_SPEED));
        }
        if(controls.right) {
            position.setRotation(position.getRotation() - (deltaInSec * ROTATION_SPEED));
        }

        /*
        if(controls.shoot) {
            if(timeToFire <= 0) {
                //EntityFactory.createPlayerBullet(world, position.x - 27, position.y + 2).addToWorld();
                //EntityFactory.createPlayerBullet(world, position.x+27, position.y+2).addToWorld();
                timeToFire = FireRate;
            }
        }
        if(timeToFire > 0) {
            timeToFire -= world.delta;
            if(timeToFire < 0) {
                timeToFire = 0;
            }
        }
        */
    }
}
