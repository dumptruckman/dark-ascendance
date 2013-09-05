package com.dumptruckman.darkascendance.shared.network;

import recs.EntitySystem;

class TimeKeepingSystem extends EntitySystem {

    private KryoNetwork kryoNetwork;

    public TimeKeepingSystem(KryoNetwork kryoNetwork) {
        this.kryoNetwork = kryoNetwork;
    }

    @Override
    protected void processSystem(final float deltaInSec) {
        long currentTime = kryoNetwork.getCurrentTime();
        currentTime += deltaInSec * 1000;
        kryoNetwork.setCurrentTime(currentTime);
    }
}
