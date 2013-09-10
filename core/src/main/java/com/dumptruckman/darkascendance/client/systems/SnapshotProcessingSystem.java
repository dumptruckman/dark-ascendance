package com.dumptruckman.darkascendance.client.systems;

import com.badlogic.gdx.utils.TimeUtils;
import com.dumptruckman.darkascendance.shared.Entity;
import com.dumptruckman.darkascendance.shared.components.Component;
import com.dumptruckman.darkascendance.client.ClientLogicLoop;
import com.dumptruckman.darkascendance.shared.messages.SnapshotMessage;
import recs.IntervalEntitySystem;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SnapshotProcessingSystem extends IntervalEntitySystem {

    private static Queue<SnapshotMessage> snapshotQueue = new ConcurrentLinkedQueue<SnapshotMessage>();
    private static long serverTime = 0L;
    private static long lastClientTime = 0L;
    private static long clientTime = 0L;
    private static long clientTimeDiff = 0L;

    public SnapshotProcessingSystem(final float intervalInSec) {
        super(intervalInSec);
    }

    @Override
    protected void processSystem(final float deltaInSec) {
        updateTime();
        if (serverTime == 0L || clientTimeDiff == 0L) {
            return;
        }
        long clientPlusServerTime = clientTimeDiff + clientTime;
        SnapshotMessage snapshot;
        while ((snapshot = pollNextSnapshot()) != null) {
            long snapshotTime = snapshot.getTime();
            if (snapshotTime >= clientPlusServerTime && snapshotTime >= serverTime) {
                processSnapshot(snapshot);
                serverTime = snapshot.getTime();
            } else {
                System.out.println("Discarded snapshot that was behind by " + ((snapshotTime - clientPlusServerTime) / 1000000L) + "ms");
            }
        }
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

    private SnapshotMessage pollNextSnapshot() {
        return snapshotQueue.poll();
    }

    public static void addSnapshot(SnapshotMessage snapshot) {
        snapshotQueue.add(snapshot);
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
