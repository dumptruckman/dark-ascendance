package com.dumptruckman.darkascendance.core.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.dumptruckman.darkascendance.core.IntervalEntityProcessingSystem;
import com.dumptruckman.darkascendance.core.components.Controls;
import com.dumptruckman.darkascendance.core.components.Player;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Thrusters;
import com.dumptruckman.darkascendance.core.components.Velocity;

public class ControlsSystem extends IntervalEntityProcessingSystem {

    private static final float ROTATION_SPEED = 80F;

    @Mapper
    ComponentMapper<Controls> controlsMap;
    @Mapper
    ComponentMapper<Position> positionMap;
    @Mapper
    ComponentMapper<Thrusters> thrustMap;
    @Mapper
    ComponentMapper<Velocity> velocityMap;

    private float timeToFire;

    public ControlsSystem(float interval) {
        super(Aspect.getAspectForAll(Controls.class, Position.class, Player.class, Velocity.class), interval);
    }

    @Override
    protected void process(Entity entity) {
        Controls controls = controlsMap.get(entity);
        Position position = positionMap.get(entity);
        Velocity velocity = velocityMap.get(entity);
        Thrusters thrusters = thrustMap.getSafe(entity);

        if (thrusters != null) {
            if(controls.up) {
                thrusters.setThrustLevel(1F);
            } else {
                thrusters.setThrustLevel(0F);
            }
        }
        if(controls.down) {
            position.attainRotation(Velocity.getRotationRequiredToReverseVelocity(velocity), (interval * ROTATION_SPEED));
        }
        if(controls.left) {
            position.setRotation(position.getRotation() + (interval * ROTATION_SPEED));
        }
        if(controls.right) {
            position.setRotation(position.getRotation() - (interval * ROTATION_SPEED));
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
