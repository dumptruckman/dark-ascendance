package com.dumptruckman.darkascendance.network.client.systems;

import com.dumptruckman.darkascendance.core.Entity;
import com.dumptruckman.darkascendance.core.components.Component;
import com.dumptruckman.darkascendance.network.client.ClientLogicLoop;
import com.dumptruckman.darkascendance.network.messages.SnapshotMessage;
import recs.IntervalEntitySystem;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SnapshotApplicationSystem extends IntervalEntitySystem {

    private static Queue<SnapshotMessage> snapshotQueue = new ConcurrentLinkedQueue<SnapshotMessage>();

    public SnapshotApplicationSystem(final float intervalInSec) {
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

}
