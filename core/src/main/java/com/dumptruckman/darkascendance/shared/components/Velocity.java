package com.dumptruckman.darkascendance.shared.components;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Velocity extends Component implements Cloneable {

    float x = 0;
    float y = 0;
    float maxX = 200;
    float maxY = 200;

    public void copyState(Component component) {
        if (component instanceof Velocity) {
            copyState((Velocity) component);
        }
    }

    public void copyState(Velocity velocity) {
        this.x = velocity.x;
        this.y = velocity.y;
        this.maxX = velocity.maxX;
        this.maxY = velocity.maxY;
    }

    public static float getRotationRequiredToReverseVelocity(Velocity v) {
        float x = -v.x;
        float y = -v.y;
        double rotation = 0;
        if (x != 0) {
            if (x < 0) {
                rotation = 0 * MathUtils.PI;
            } else {
                rotation = 2 * MathUtils.PI;
            }
            rotation -= MathUtils.atan2(x, y);
        } else if (y < 0) {
            rotation = MathUtils.PI;
        }
        return (float) (rotation * MathUtils.radiansToDegrees);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Velocity setX(float x) {
        this.x = x;
        return this;
    }

    public Velocity setY(float y) {
        this.y = y;
        return this;
    }

    public Velocity addToX(float amount) {
        this.x = MathUtils.clamp(this.x + amount, -maxX, maxX);
        return this;
    }

    public Velocity addToY(float amount) {
        this.y = MathUtils.clamp(this.y + amount, -maxY, maxY);
        return this;
    }

    public float getMaxX() {
        return maxX;
    }

    public float getMaxY() {
        return maxY;
    }

    public Velocity setMaxX(float x) {
        this.maxX = x;
        return this;
    }

    public Velocity setMaxY(float y) {
        this.maxY = y;
        return this;
    }

    @Override
    public Velocity clone() {
        return (Velocity) super.clone();
    }
}
