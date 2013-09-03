package com.dumptruckman.darkascendance.shared;


import com.dumptruckman.darkascendance.shared.components.Component;

import java.util.Arrays;

public class Entity extends recs.Entity {

    public void addComponent(final Component... components) {
        super.addComponent((recs.Component[]) components);
        for (Component component : components) {
            component.setEntityId(getId());
        }
    }

    @Override
    public String toString() {
        StringBuilder components = new StringBuilder();
        for (recs.Component component : getComponents()) {
            if (components.length() != 0) {
                components.append(", ");
            }
            components.append(component.getClass());
        }
        return "Entity{" +
                "entityId=" + getId() +
                ", components=[" + components + "]" +
                "}";
    }
}
