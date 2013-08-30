package com.dumptruckman.darkascendance.core.components;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Velocity extends Component {

    Vector2 vector = new Vector2(0, 0);
    private Vector2 max = new Vector2(200f, 200f);

    public static float getRotationRequiredToReverseVelocity(Velocity v) {
        float x = -v.vector.x;
        float y = -v.vector.y;
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
        return vector.x;
    }

    public float getY() {
        return vector.y;
    }

    public Velocity setX(float x) {
        vector.x = x;
        return this;
    }

    public Velocity setY(float y) {
        vector.y = y;
        return this;
    }

    public Velocity addToX(float amount) {
        vector.x = MathUtils.clamp(vector.x + amount, -max.x, max.x);
        return this;
    }

    public Velocity addToY(float amount) {
        vector.y = MathUtils.clamp(vector.y + amount, -max.y, max.y);
        return this;
    }

    public float getMaxX() {
        return max.x;
    }

    public float getMaxY() {
        return max.y;
    }

    public Velocity setMaxX(float x) {
        max.x = x;
        return this;
    }

    public Velocity setMaxY(float y) {
        max.y = y;
        return this;
    }
}
