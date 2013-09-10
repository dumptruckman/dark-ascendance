package com.dumptruckman.darkascendance.server.systems;

import com.dumptruckman.darkascendance.server.ServerLogicLoop;
import com.dumptruckman.darkascendance.shared.components.Component;
import com.dumptruckman.darkascendance.shared.messages.MessageFactory;
import com.dumptruckman.darkascendance.server.GameServer;
import com.dumptruckman.darkascendance.shared.snapshot.Snapshot;
import recs.IntervalEntitySystem;

import java.util.concurrent.locks.ReentrantLock;

public class SnapshotCreationSystem extends IntervalEntitySystem {

    private static long tick = 0L;
    private static Snapshot currentSnapshot = new Snapshot(tick);
    private static ReentrantLock lock = new ReentrantLock();

    private ServerLogicLoop server;

    public SnapshotCreationSystem(ServerLogicLoop server) {
        super(GameServer.SNAPSHOT_RATE);
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
        server.snapshotPrepared(snapshot);
    }

    private void resetCurrentSnapshot() {
        tick++;
        currentSnapshot = new Snapshot(tick);
    }

    public static void addChangedComponentToSnapshot(Component component) {
        if (lock.isHeldByCurrentThread()) {
            currentSnapshot.addComponent(component);
        }
    }
}
