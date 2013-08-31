package com.dumptruckman.darkascendance.core;


import com.dumptruckman.darkascendance.core.components.Component;

public class Entity extends recs.Entity {

    public void addComponent(final Component... components) {
        super.addComponent((recs.Component[]) components);
        for (Component component : components) {
            component.setEntityId(getId());
        }
    }
}
