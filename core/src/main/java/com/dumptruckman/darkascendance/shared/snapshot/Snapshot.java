package com.dumptruckman.darkascendance.shared.snapshot;

import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.dumptruckman.darkascendance.shared.components.Component;

public class Snapshot {

    private ObjectSet<Component> components = new ObjectSet<Component>();
    private long tick;

    public Snapshot(long tick) {
        this.tick = tick;
    }

    public Snapshot addComponent(Component component) {
        components.add(component);
        return this;
    }

    public ObjectSet<Component> getComponents() {
        return components;
    }

    public long getTick() {
        return tick;
    }
}
