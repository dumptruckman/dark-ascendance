package com.dumptruckman.darkascendance.network.messages;

import com.badlogic.gdx.utils.ObjectSet;
import com.dumptruckman.darkascendance.core.components.Component;
import com.dumptruckman.darkascendance.network.snapshot.Snapshot;

public class SnapshotMessage extends Message {

    private ObjectSet<Component> components;

    public SnapshotMessage copySnapshot(Snapshot snapshot) {
        ObjectSet<Component> components = snapshot.getComponents();
        this.components = new ObjectSet<Component>(components.size);
        for (Component component : components) {
            Component cloned = component.clone();
            this.components.add(cloned);
        }
        return this;
    }

    public ObjectSet<Component> getComponents() {
        return components;
    }
}
