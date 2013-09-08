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
    private static long serverTime;

    public SnapshotProcessingSystem(final float intervalInSec) {
        super(intervalInSec);
    }

    @Override
    protected void processSystem(final float deltaInSec) {
        SnapshotMessage snapshot = pollNextSnapshot();
        if (snapshot != null) {
            processSnapshot(snapshot);
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
}
