package com.dumptruckman.darkascendance.shared.network;

import com.badlogic.gdx.utils.TimeUtils;
import recs.EntitySystem;

class TimeKeepingSystem extends EntitySystem {

    private KryoNetwork kryoNetwork;

    private long lastTime = 0L;
    private long currentTimeAccumulated = 0L;

    public TimeKeepingSystem(KryoNetwork kryoNetwork) {
        this.kryoNetwork = kryoNetwork;
    }

    @Override
    protected void processSystem(final float deltaInSec) {
        if (lastTime == 0L) {
            lastTime = TimeUtils.nanoTime();
            return;
        }
        long currentTime = TimeUtils.nanoTime();
        currentTimeAccumulated += currentTime - lastTime;
        lastTime = currentTime;
        kryoNetwork.setCurrentTime(currentTimeAccumulated);
    }
}
