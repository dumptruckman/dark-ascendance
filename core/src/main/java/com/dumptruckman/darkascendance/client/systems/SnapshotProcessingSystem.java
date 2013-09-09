package com.dumptruckman.darkascendance.client.systems;

import com.dumptruckman.darkascendance.shared.Entity;
import com.dumptruckman.darkascendance.shared.components.Component;
import com.dumptruckman.darkascendance.client.ClientLogicLoop;
import com.dumptruckman.darkascendance.shared.messages.SnapshotMessage;
import recs.IntervalEntitySystem;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SnapshotProcessingSystem extends IntervalEntitySystem {

    private static Queue<SnapshotMessage> snapshotQueue = new ConcurrentLinkedQueue<SnapshotMessage>();
    private static long serverTime = -1L;
    private static long clientTime = 0L;

    public SnapshotProcessingSystem(final float intervalInSec) {
        super(intervalInSec);
    }

    @Override
    protected void processSystem(final float deltaInSec) {
        if (serverTime == -1L) {
            return;
        }
        SnapshotMessage snapshot;
        while ((snapshot = pollNextSnapshot()) != null) {
            if (snapshot.getTime() > serverTime) {
                processSnapshot(snapshot);
                serverTime = snapshot.getTime();
                break;
            } else {
                System.out.println("Discarded old snapshot: " + snapshot);
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
        serverTime = time;
    }

    public static void incrementClientTime(float deltaInSec) {
        clientTime += deltaInSec * 1000000000L;
    }
}
