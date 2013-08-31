package com.dumptruckman.darkascendance.network.snapshot;

import com.badlogic.gdx.utils.ObjectSet;
import com.dumptruckman.darkascendance.core.components.Component;

public class Snapshot {

    private ObjectSet<Component> components = new ObjectSet<Component>();

    public void addComponent(Component component) {
        components.add(component);
    }

    public ObjectSet<Component> getComponents() {
        return components;
    }
}
