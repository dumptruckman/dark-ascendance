package com.dumptruckman.darkascendance.core.components;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;

public class Velocity extends Component {
    public float vectorX = 0f;
    public float vectorY = 0f;
    public float maxX = 60f;
    public float maxY = 60f;

    public float getRotationRequiredToReverseVelocity() {
        float x = -vectorX;
        float y = -vectorY;
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
}
