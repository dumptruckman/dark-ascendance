package com.dumptruckman.darkascendance.shared.components;

import com.dumptruckman.darkascendance.shared.snapshot.Snapshot;

public class SnapshotHolder extends Component {

    private float snapshotUpdateRate = .05f;
    private float snapshotUpdateRateAccumulator = 0f;
    private Snapshot currentSnapshot = new Snapshot();

    public float getSnapshotUpdateRateDelta() {
        return snapshotUpdateRate;
    }

    public SnapshotHolder increaseSnapshotUpdateRateDelta(float additionalSeconds) {
        snapshotUpdateRate += additionalSeconds;
        return this;
    }

    public boolean isReadyForSnapshotUpdate() {
        return snapshotUpdateRateAccumulator >= snapshotUpdateRate;
    }

    public void snapshotUpdated() {
        snapshotUpdateRateAccumulator -= snapshotUpdateRate;
    }

    private Snapshot getSnapshot() {
        return currentSnapshot;
    }

    private void resetCurrentSnapshot() {
        currentSnapshot = new Snapshot();
    }
}
