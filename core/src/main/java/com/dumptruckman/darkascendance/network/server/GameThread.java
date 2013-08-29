package com.dumptruckman.darkascendance.network.server;

import com.dumptruckman.darkascendance.util.TickRateController;

public class GameThread extends Thread {

    public static final long TICK_LENGTH_MILLIS = 15L;

    private TickRateController tickRateController = new TickRateController(TICK_LENGTH_MILLIS);

    @Override
    public void run() {
        while (true) {
            waitForTickIfNecessary();
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
