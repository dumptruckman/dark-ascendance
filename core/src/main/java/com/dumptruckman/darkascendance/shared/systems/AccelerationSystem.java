package com.dumptruckman.darkascendance.shared.systems;

import com.badlogic.gdx.math.MathUtils;
import com.dumptruckman.darkascendance.server.systems.SnapshotCreationSystem;
import com.dumptruckman.darkascendance.shared.components.Position;
import com.dumptruckman.darkascendance.shared.components.Thrusters;
import com.dumptruckman.darkascendance.shared.components.Velocity;
import recs.ComponentMapper;
import recs.IntervalEntitySystem;

public class AccelerationSystem extends IntervalEntitySystem {

    ComponentMapper<Velocity> velocityMap;
    ComponentMapper<Position> positionMap;
    ComponentMapper<Thrusters> thrustersMap;
    private boolean createSnapshots;

    public AccelerationSystem(float interval, boolean createSnapshots) {
        super(interval, Velocity.class, Position.class);
        this.createSnapshots = createSnapshots;
    }

    @Override
    protected void processEntity(int entityId, float deltaInSec) {
        Velocity velocity = velocityMap.get(entityId);
        Position position = positionMap.get(entityId);
        Thrusters thrusters = thrustersMap.get(entityId);

        float totalForwardAcceleration = 0F;
        totalForwardAcceleration += getThrustersAcceleration(thrusters);
        totalForwardAcceleration = modifyAccelerationByDelta(totalForwardAcceleration, deltaInSec);

        float facingAngleRadians = MathUtils.degreesToRadians * position.getRotation();

        addForwardAccelerationToVelocity(totalForwardAcceleration, velocity, facingAngleRadians);
    }

    float getThrustersAcceleration(Thrusters thrusters) {
        float acceleration = 0F;
        if (thrusters != null) {
            acceleration += thrusters.getAccelerationAddedByThrust();
        }
        return acceleration;
    }

    float modifyAccelerationByDelta(float acceleration, float delta) {
        return acceleration * delta;
    }

    void addForwardAccelerationToVelocity(float acceleration, Velocity velocity, float facingAngleRadians) {
        if (acceleration == 0F) {
            return;
        }

        velocity.addToX(acceleration * -(float) MathUtils.sin(facingAngleRadians));
        velocity.addToY(acceleration * (float) MathUtils.cos(facingAngleRadians));
        if (createSnapshots) {
            SnapshotCreationSystem.addChangedComponentToSnapshot(velocity);
        }
    }
}
