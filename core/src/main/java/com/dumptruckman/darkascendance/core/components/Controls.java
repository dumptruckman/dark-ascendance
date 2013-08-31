package com.dumptruckman.darkascendance.core.components;

public class Controls extends Component implements Cloneable {

    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;

    public void copyState(Component component) {
        if (component instanceof Controls) {
            copyState((Controls) component);
        }
    }

    public void copyState(Controls controls) {
        this.up = controls.up;
        this.down = controls.down;
        this.left = controls.left;
        this.right = controls.right;
    }

    public Controls up(boolean up) {
        this.up = up;
        return this;
    }

    public Controls down(boolean down) {
        this.down = down;
        return this;
    }

    public Controls left(boolean left) {
        this.left = left;
        return this;
    }

    public Controls right(boolean right) {
        this.right = right;
        return this;
    }

    public boolean up() {
        return up;
    }

    public boolean down() {
        return down;
    }

    public boolean left() {
        return left;
    }

    public boolean right() {
        return right;
    }

    @Override
    public Controls clone() {
        return (Controls) super.clone();
    }
}
