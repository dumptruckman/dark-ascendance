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
    ComponentMapper<Thrust> tm;
    @Mapper
    ComponentMapper<Velocity> vm;
    @Mapper
    ComponentMapper<Position> pm;

    public ThrustSystem() {
        super(Aspect.getAspectForAll(Thrust.class, Velocity.class, Position.class));
    }

    @Override
    protected void process(Entity e) {
        Thrust thrust = tm.get(e);
        Velocity velocity = vm.get(e);
        Position position = pm.get(e);

        processThrustForEntity(e, thrust, velocity, position, world.getDelta());
    }

    void processThrustForEntity(Entity e, Thrust thrust, Velocity velocity, Position position, float delta) {
        if (thrust.forwardThrust == 0F) {
            return;
        }

        float deltaThrust = world.getDelta() * thrust.forwardThrust;
        float r = MathUtils.degreesToRadians * position.getRotation();

        velocity.vectorX += MathUtils.clamp(deltaThrust * -(float)MathUtils.sin(r), -velocity.maxX, velocity.maxX);
        velocity.vectorY += MathUtils.clamp(deltaThrust * (float)MathUtils.cos(r), -velocity.maxY, velocity.maxY);
    }
}
