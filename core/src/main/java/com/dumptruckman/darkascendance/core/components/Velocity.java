package com.dumptruckman.darkascendance.core.components;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Velocity extends Component {

    Vector2 vector = new Vector2(0, 0);
    private Vector2 max = new Vector2(60f, 60f);

    public float getRotationRequiredToReverseVelocity() {
        float x = -vector.x;
        float y = -vector.y;
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

    public Velocity addToX(float amount) {
        vector.set(MathUtils.clamp(vector.x + amount, -max.x, max.x), vector.y);
        return this;
    }

    public Velocity addToY(float amount) {
        vector.set(vector.x, MathUtils.clamp(vector.y + amount, -max.y, max.y));
        return this;
    }
}
