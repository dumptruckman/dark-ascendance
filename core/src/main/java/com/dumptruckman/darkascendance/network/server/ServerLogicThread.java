package com.dumptruckman.darkascendance.network.server;

import com.artemis.World;
import com.dumptruckman.darkascendance.core.GameLogic;
import com.dumptruckman.darkascendance.util.TickRateController;

public class ServerLogicThread extends Thread {

    public static void main(String[] args) {
        new ServerLogicThread().run();
    }

    private static final float NANOS_IN_SECOND = 1000000000F;

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
            gameLogic.getWorld().setDelta(tickRateController.getDelta() / NANOS_IN_SECOND);
            gameLogic.processTick();
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
