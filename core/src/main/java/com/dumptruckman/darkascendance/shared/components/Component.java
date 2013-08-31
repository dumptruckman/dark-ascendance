package com.dumptruckman.darkascendance.shared.components;

public class Component extends recs.Component implements Cloneable {

    private int entityId;

    public int getEntityId() {
        return entityId;
    }

    public Component setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public void copyState(Component component) { }

    public Component clone() {
        try {
            return (Component) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
