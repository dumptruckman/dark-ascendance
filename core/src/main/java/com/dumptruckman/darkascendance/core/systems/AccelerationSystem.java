package com.dumptruckman.darkascendance.core.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Thrusters;
import com.dumptruckman.darkascendance.core.components.Velocity;

public class AccelerationSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Thrusters> thrustMap;
    @Mapper
    ComponentMapper<Velocity> velocityMap;
    @Mapper
    ComponentMapper<Position> positionMap;

    public AccelerationSystem() {
        super(Aspect.getAspectForAll(Velocity.class, Position.class));
    }

    @Override
    protected void process(Entity entity) {
        Velocity velocity = velocityMap.get(entity);
        Position position = positionMap.get(entity);
        Thrusters thrusters = thrustMap.getSafe(entity);

        float totalForwardAcceleration = 0F;
        totalForwardAcceleration += getThrustersAcceleration(thrusters);
        totalForwardAcceleration = modifyAccelerationByDelta(totalForwardAcceleration, world.getDelta());

        float facingAngleRadians = MathUtils.degreesToRadians * position.getRotation();

        addForwardAccelerationToVelocity(totalForwardAcceleration, velocity, facingAngleRadians);
    }

    static float getThrustersAcceleration(Thrusters thrusters) {
        float acceleration = 0F;
        if (thrusters != null) {
            acceleration += thrusters.getAccelerationAddedByThrust();
        }
        return acceleration;
    }

    static float modifyAccelerationByDelta(float acceleration, float delta) {
        return acceleration * delta;
    }

    static void addForwardAccelerationToVelocity(float acceleration, Velocity velocity, float facingAngleRadians) {
        if (acceleration == 0F) {
            return;
        }

        velocity.addToX(acceleration * -(float)MathUtils.sin(facingAngleRadians));
        velocity.addToY(acceleration * (float)MathUtils.cos(facingAngleRadians));
    }
}
