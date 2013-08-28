package com.dumptruckman.darkascendance.util;

import com.badlogic.gdx.utils.TimeUtils;

public class TickRateHandler {

    private long lastCycleTime = 0L;
    private long tickLength;
    private long currentTime;

    public TickRateHandler(long tickLength) {
        this.tickLength = tickLength;
    }

    public void waitForTickIfNecessary() {
        currentTime = TimeUtils.nanoTime();

        if (lastCycleTime == 0L) {
            lastCycleTime = currentTime;
            return;
        }

        return currentTime - lastCycleTime < tickLength;
    }
}
