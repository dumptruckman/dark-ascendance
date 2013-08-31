package com.dumptruckman.darkascendance.core.components;

public class Controls {

    private int entityId;

    public boolean up = false;
    public boolean down = false;
    public boolean left = false;
    public boolean right = false;

    public int getEntityId() {
        return entityId;
    }

    public Controls setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public void copyControls(Controls controls) {
        this.up = controls.up;
        this.down = controls.down;
        this.left = controls.left;
        this.right = controls.right;
    }
}
