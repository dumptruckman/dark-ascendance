package com.dumptruckman.darkascendance.server.systems;

import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.shared.components.Position;
import com.dumptruckman.darkascendance.shared.components.Thrusters;
import com.dumptruckman.darkascendance.shared.components.Velocity;
import recs.ComponentMapper;
import recs.IntervalEntitySystem;

public class ControlsSystem extends IntervalEntitySystem {

    private static final float ROTATION_SPEED = 80F;

    ComponentMapper<Controls> controlsMap;
    ComponentMapper<Position> positionMap;
    ComponentMapper<Velocity> velocityMap;
    ComponentMapper<Thrusters> thrustersMap;

    private float timeToFire;

    public ControlsSystem(float interval) {
        super(interval, Controls.class, Position.class, Velocity.class);
    }

    @Override
    protected void processEntity(int entityId, float deltaInSec) {
        Controls controls = controlsMap.get(entityId);
        Position position = positionMap.get(entityId);
        Velocity velocity = velocityMap.get(entityId);
        Thrusters thrusters = thrustersMap.get(entityId);

        if (thrusters != null) {
            if(controls.up()) {
                if (thrusters.getThrustLevel() != 1F) {
                    thrusters.setThrustLevel(1F);
                    SnapshotCreationSystem.addChangedComponentToSnapshot(thrusters);
                }
            } else {
                if (thrusters.getThrustLevel() != 0F) {
                    thrusters.setThrustLevel(0F);
                    SnapshotCreationSystem.addChangedComponentToSnapshot(thrusters);
                }
            }

        }
        if(controls.down()) {
            position.attainRotation(Velocity.getRotationRequiredToReverseVelocity(velocity), (deltaInSec * ROTATION_SPEED));
        }
        if(controls.left()) {
            position.setRotation(position.getRotation() + (deltaInSec * ROTATION_SPEED));
        }
        if(controls.right()) {
            position.setRotation(position.getRotation() - (deltaInSec * ROTATION_SPEED));
        }

        if (controls.down() || controls.right() || controls.left()) {
            SnapshotCreationSystem.addChangedComponentToSnapshot(position);
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