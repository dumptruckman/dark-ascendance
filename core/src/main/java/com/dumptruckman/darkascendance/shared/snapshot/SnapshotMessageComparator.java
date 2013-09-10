package com.dumptruckman.darkascendance.shared.snapshot;

import com.dumptruckman.darkascendance.shared.messages.SnapshotMessage;

import java.util.Comparator;

public class SnapshotMessageComparator implements Comparator<SnapshotMessage> {

    @Override
    public int compare(final SnapshotMessage o1, final SnapshotMessage o2) {
        if (o1.getTick() < o2.getTick()) {
            return -1;
        } else if (o1.getTick() == o2.getTick()) {
            return 0;
        } else {
            return 1;
        }
    }
}
