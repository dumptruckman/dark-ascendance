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

        processThrust(thrust, velocity, position, world.getDelta());
    }

    static void processThrust(Thrust thrust, Velocity velocity, Position position, float delta) {
        if (thrust.getThrust() == 0F) {
            return;
        }

        float deltaThrust = delta * thrust.getThrust();
        float r = MathUtils.degreesToRadians * position.getRotation();

        velocity.addToX(deltaThrust * -(float)MathUtils.sin(r));
        velocity.addToY(deltaThrust * (float)MathUtils.cos(r));
    }
}
