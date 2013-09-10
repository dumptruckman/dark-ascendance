package com.dumptruckman.darkascendance.client.systems;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.TimeUtils;
import com.dumptruckman.darkascendance.client.GameClient;
import com.dumptruckman.darkascendance.server.GameServer;
import com.dumptruckman.darkascendance.shared.Entity;
import com.dumptruckman.darkascendance.shared.components.Component;
import com.dumptruckman.darkascendance.client.ClientLogicLoop;
import com.dumptruckman.darkascendance.shared.messages.SnapshotMessage;
import com.dumptruckman.darkascendance.shared.snapshot.SnapshotMessageComparator;
import recs.IntervalEntitySystem;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class SnapshotProcessingSystem extends IntervalEntitySystem {

    private final static int RENDER_TICK_DELAY = MathUtils.ceil((GameClient.INTERPOLATION_LENGTH / GameServer.SNAPSHOT_RATE));

    private static SortedMap<Long, SnapshotMessage> snapshotQueue = new TreeMap<Long, SnapshotMessage>();
    private static long renderTick = 0L;
    private static long clientTick = 0L;
    private static SnapshotMessage renderingFrom = null;
    private static SnapshotMessage renderingTo = null;

    private static long serverTime = 0L;
    private static long lastClientTime = 0L;
    private static long clientTime = 0L;
    private static long clientTimeDiff = 0L;

    public SnapshotProcessingSystem() {
        super(GameServer.SNAPSHOT_RATE);
    }

    @Override
    protected void processSystem(final float deltaInSec) {
        updateTime();
        if (clientTick == 0L) {
            return;
        }
        if (clientTick < renderTick + RENDER_TICK_DELAY) {
            clientTick++;
            return;
        }

        if (renderingFrom == null) {
            renderingFrom = snapshotQueue.get(snapshotQueue.firstKey());
        }
        /*
        long clientPlusServerTime = clientTimeDiff + clientTime;
        Iterator<SnapshotMessage> iterator = snapshotQueue.iterator();
        while (iterator.hasNext()) {
            SnapshotMessage snapshot = iterator.next();
            long snapshotTime = snapshot.getTime();
            if (snapshotTime >= clientPlusServerTime && snapshotTime >= serverTime) {
                processSnapshot(snapshot);
                serverTime = snapshot.getTime();
            } else {
                System.out.println("Discarded snapshot that was behind by " + ((snapshotTime - clientPlusServerTime) / 1000000L) + "ms");
            }
        }
        */
        incrementTicks();
    }

    private void incrementTicks() {
        renderTick++;
        clientTick++;
    }

    private void processSnapshot(SnapshotMessage snapshot) {
        for (Component updateComponent : snapshot.getComponents()) {
            Entity entity = ClientLogicLoop.getEntityByServerEntityId(updateComponent.getEntityId());
            if (entity != null) {
                Component component = entity.getComponent(updateComponent.getClass());
                component.copyState(updateComponent);
            }
        }
    }

    public static void addSnapshot(SnapshotMessage snapshot) {
        long tick = snapshot.getTick();
        if (clientTick == 0L) {
            clientTick = tick;
            renderTick = tick;
        }
        if (tick >= renderTick) {
            snapshotQueue.put(snapshot.getTick(), snapshot);
        } else {
            System.out.println("Discarding snapshot for tick " + tick + ".  Client is currently rendering tick " + renderTick + ".");
        }
    }

    public static void setServerTime(long time) {
        if (serverTime == 0L) {
            clientTimeDiff = time - clientTime;
        }
        serverTime = time;
    }

    public static void updateTime() {
        if (lastClientTime == 0L) {
            lastClientTime = TimeUtils.nanoTime();
            return;
        }
        long currentTime = TimeUtils.nanoTime();
        clientTime += currentTime - lastClientTime;
        lastClientTime = currentTime;
    }
}
