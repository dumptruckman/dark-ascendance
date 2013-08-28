package com.dumptruckman.darkascendance.core.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;
import com.badlogic.gdx.math.MathUtils;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Thrust;
import com.dumptruckman.darkascendance.core.components.Velocity;

public class ThrustSystem extends EntityProcessingSystem {

    @Mapper
    ComponentMapper<Thrust> thrustMap;
    @Mapper
    ComponentMapper<Velocity> velocityMap;
    @Mapper
    ComponentMapper<Position> positionMap;

    public ThrustSystem() {
        super(Aspect.getAspectForAll(Thrust.class, Velocity.class, Position.class));
    }

    @Override
    protected void process(Entity entity) {
        Thrust thrust = thrustMap.get(entity);
        Velocity velocity = velocityMap.get(entity);
        Position position = positionMap.get(entity);

        processThrust(thrust, velocity, position, world.getDelta());
    }

    static void processThrust(Thrust thrust, Velocity velocity, Position position, float delta) {
        if (thrust.getCurrentThrust() == 0F) {
            return;
        }

        float deltaThrust = delta * thrust.getCurrentThrust();
        float r = MathUtils.degreesToRadians * position.getRotation();

        velocity.addToX(deltaThrust * -(float)MathUtils.sin(r));
        velocity.addToY(deltaThrust * (float)MathUtils.cos(r));
    }
}
