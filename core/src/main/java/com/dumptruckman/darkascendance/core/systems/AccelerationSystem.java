package com.dumptruckman.darkascendance.core.systems;

import com.badlogic.gdx.math.MathUtils;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Thrusters;
import com.dumptruckman.darkascendance.core.components.Velocity;
import com.dumptruckman.darkascendance.recs.ComponentMapper;
import com.dumptruckman.darkascendance.recs.Entity;
import com.dumptruckman.darkascendance.recs.IntervalEntitySystem;

public class AccelerationSystem extends IntervalEntitySystem {

    ComponentMapper<Thrusters> thrustMap;
    ComponentMapper<Velocity> velocityMap;
    ComponentMapper<Position> positionMap;

    public AccelerationSystem(float interval) {
        super(interval, Velocity.class, Position.class);
    }

    @Override
    protected void processEntity(int entityId, float deltaInSec) {
        Velocity velocity = velocityMap.get(entityId);
        Position position = positionMap.get(entityId);
        Thrusters thrusters = thrustMap.get(entityId);

        float totalForwardAcceleration = 0F;
        totalForwardAcceleration += getThrustersAcceleration(thrusters);
        totalForwardAcceleration = modifyAccelerationByDelta(totalForwardAcceleration, deltaInSec);

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

        velocity.addToX(acceleration * -(float) MathUtils.sin(facingAngleRadians));
        velocity.addToY(acceleration * (float) MathUtils.cos(facingAngleRadians));
    }
}
