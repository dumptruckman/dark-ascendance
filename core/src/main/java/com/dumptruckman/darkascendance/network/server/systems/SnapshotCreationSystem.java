package com.dumptruckman.darkascendance.network.server.systems;

import com.dumptruckman.darkascendance.core.components.Component;
import com.dumptruckman.darkascendance.network.messages.MessageFactory;
import com.dumptruckman.darkascendance.network.server.GameServer;
import com.dumptruckman.darkascendance.network.snapshot.Snapshot;
import recs.IntervalEntitySystem;

import java.util.concurrent.locks.ReentrantLock;

public class SnapshotCreationSystem extends IntervalEntitySystem {

    private static Snapshot currentSnapshot = new Snapshot();
    private static ReentrantLock lock = new ReentrantLock();

    private GameServer server;

    public SnapshotCreationSystem(GameServer server, float intervalInSec) {
        super(intervalInSec);
        this.server = server;
    }

    public static void lockToThread() {
        lock.tryLock();
    }

    @Override
    protected void processSystem(final float deltaInSec) {
        sendSnapshot(currentSnapshot);
        resetCurrentSnapshot();
    }

    private void sendSnapshot(Snapshot snapshot) {
        server.sendMessageToAll(MessageFactory.createSnapshot(snapshot));
    }

    private void resetCurrentSnapshot() {
        currentSnapshot = new Snapshot();
    }

    public static void addChangedComponentToSnapshot(Component component) {
        if (lock.isHeldByCurrentThread()) {
            currentSnapshot.addComponent(component);
        }
    }
}
