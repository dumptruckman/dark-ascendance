package com.dumptruckman.darkascendance.core;

import com.artemis.Aspect;
import com.artemis.EntitySystem;

public abstract class IntervalEntitySystem extends EntitySystem {

    private float acc;
    protected float interval;

    public IntervalEntitySystem(Aspect aspect, float interval) {
        super(aspect);
        this.interval = interval;
    }

    @Override
    protected boolean checkProcessing() {
        acc += world.getDelta();
        if(acc >= interval) {
            acc -= interval;
            return true;
        }
        return false;
    }
}
