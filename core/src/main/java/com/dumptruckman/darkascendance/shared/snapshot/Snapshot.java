package com.dumptruckman.darkascendance.shared.snapshot;

import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.ObjectIntMap;
import com.badlogic.gdx.utils.ObjectSet;
import com.dumptruckman.darkascendance.shared.components.Component;

public class Snapshot {



    private ObjectSet<Component> components = new ObjectSet<Component>();

    public void addComponent(Component component) {
        components.add(component);
    }

    public ObjectSet<Component> getComponents() {
        return components;
    }
}
