package com.dumptruckman.darkascendance.network.server;

import com.artemis.World;
import com.dumptruckman.darkascendance.core.GameLogic;
import com.dumptruckman.darkascendance.util.TickRateController;

public class ServerLogicThread extends Thread {

    public static void main(String[] args) {
        new ServerLogicThread().run();
    }

    private TickRateController tickRateController = new TickRateController(GameLogic.TICK_LENGTH_MILLIS);
    private GameLogic gameLogic;

    public ServerLogicThread() {
        World world = new World();
        gameLogic = new GameLogic(world, false);

        gameLogic.intializeLogicSystems();
    }

    @Override
    public void run() {
        while (true) {
            waitForTickIfNecessary();
            gameLogic.processTick(tickRateController.getDelta());
        }
    }

    private void waitForTickIfNecessary() {
        try {
            tickRateController.waitForTickIfNecessary();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
