package com.dumptruckman.darkascendance.network.server;

import com.artemis.World;
import com.dumptruckman.darkascendance.core.GameLogic;

class ServerLogicLoop extends GameLogic implements Runnable {

    private volatile boolean readyForNetworking = false;

    public ServerLogicLoop() {
        super(new World(), false);
        intializeLogicSystems();
    }

    @Override
    public void run() {
        while (true) {
            waitForTickIfNecessary();
            processTick(getTickRateDelta());

            if (!readyForNetworking) {
                readyForNetworking = true;
            }
        }
    }

    public boolean isReadyForNetworking() {
        return readyForNetworking;
    }

    public void playerConnected(int connectionId) {

    }
}
