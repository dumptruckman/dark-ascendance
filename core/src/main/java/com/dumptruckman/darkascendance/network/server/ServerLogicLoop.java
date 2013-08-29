package com.dumptruckman.darkascendance.network.server;

import com.artemis.World;
import com.dumptruckman.darkascendance.core.GameLogic;
import com.dumptruckman.darkascendance.util.TickRateController;

class ServerLogicLoop extends Thread {

    private TickRateController tickRateController = new TickRateController(GameLogic.TICK_LENGTH_MILLIS);
    private GameLogic gameLogic;

    private volatile boolean readyForNetworking = false;

    public ServerLogicLoop() {
        World world = new World();
        gameLogic = new GameLogic(world, false);

        gameLogic.intializeLogicSystems();
    }

    public void run() {
        while (true) {
            waitForTickIfNecessary();
            gameLogic.processTick(tickRateController.getDelta());

            if (!readyForNetworking) {
                readyForNetworking = true;
            }
        }
    }

    private void waitForTickIfNecessary() {
        try {
            tickRateController.waitForTickIfNecessary();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public boolean isReadyForNetworking() {
        return readyForNetworking;
    }

    public void playerConnected(int connectionId) {

    }
}
