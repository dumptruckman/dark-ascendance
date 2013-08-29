package com.dumptruckman.darkascendance.util;

import com.badlogic.gdx.utils.TimeUtils;

public class TickRateController {

    private static final long NANOS_IN_MILLI = 1000000L;

    private final long tickLengthMillis;
    private long lastCycleTime = 0L;

    public TickRateController(long tickLengthMillis) {
        this.tickLengthMillis = tickLengthMillis;
    }

    public boolean hasTickElapsed() {
        long delta = getDelta();
        //System.out.println("delta: " + delta);
        return delta >= tickLengthMillis;
    }

    public long getDelta() {
        return (TimeUtils.nanoTime() - lastCycleTime) / NANOS_IN_MILLI;
    }

    public void waitForTickIfNecessary() throws InterruptedException {
        if (lastCycleTime == 0L) {
            lastCycleTime = TimeUtils.nanoTime();
            return;
        }

        if (!hasTickElapsed()) {
            long delta = getDelta();
            // TODO temporary debug output
            System.out.println("waiting " + delta + " ms for next tick");
            Thread.sleep(delta);
        }

        prepareForNextTick();
    }

    public void prepareForNextTick() {
        lastCycleTime = TimeUtils.nanoTime();
    }
}
