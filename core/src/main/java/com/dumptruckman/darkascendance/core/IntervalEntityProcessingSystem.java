package com.dumptruckman.darkascendance.core;

import com.artemis.Aspect;
import com.artemis.Entity;
import com.artemis.utils.ImmutableBag;

public abstract class IntervalEntityProcessingSystem extends IntervalEntitySystem {

    public IntervalEntityProcessingSystem(Aspect aspect, float interval) {
        super(aspect, interval);
    }

    /**
     * Process a entity this system is interested in.
     * @param e the entity to process.
     */
    protected abstract void process(Entity e);


    @Override
    protected void processEntities(ImmutableBag<Entity> entities) {
        for (int i = 0, s = entities.size(); s > i; i++) {
            process(entities.get(i));
        }
    }
}
