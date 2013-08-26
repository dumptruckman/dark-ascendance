package com.dumptruckman.darkascendance.core.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.Gdx;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Sample;

public class SampleMovementSystem extends EntitySystem {

    @Mapper
    ComponentMapper<Position> pm;

    private float elapsed = 0F;

    public SampleMovementSystem() {
        super(Aspect.getAspectForAll(Position.class, Sample.class));
    }

    @Override
    protected void processEntities(ImmutableBag<Entity> entities) {
        for(int i = 0; entities.size() > i; i++) {
            process(entities.get(i));
        }
    }

    protected void process(Entity e) {
        elapsed += Gdx.graphics.getDeltaTime();
        if(pm.has(e)) {
            Position position = pm.getSafe(e);

            position.x = 100 + 100 * (float) Math.cos(elapsed);
            position.y = 100 + 25 * (float) Math.sin(elapsed);
        }
    }

    @Override
    protected boolean checkProcessing() {
        return true;
    }
}
