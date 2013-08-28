package com.dumptruckman.darkascendance.core.components;

import com.artemis.Component;

public class Position extends Component {

    private float x = 0F;
    private float y = 0F;
    private float rotation = 0F;

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getRotation() {
        return rotation;
    }

    public void setRotation(float r) {
        if (r >= 360F) {
            r = wrapCounterClockwise(r);
        } else if (r < 0F) {
            r = wrapClockwise(r);
        }
        this.rotation = r;
    }

    private float wrapCounterClockwise(float r) {
        return (r % 360F) / 360F;
    }

    private float wrapClockwise(float r) {
        return 360 - (-r % 360F);
    }

    public void attainRotation(float desiredRotation, float maxChangeAmount) {
        if (rotation == desiredRotation) {
            return;
        }
        float direction = desiredRotation - rotation;
        if (direction < -180)  {
            direction += 360;
        }
        if (direction > 180) {
            direction -= 360;
        }

        float newRotation;
        if (direction > 0) {
            newRotation = rotation + maxChangeAmount;
            if (newRotation >= 360) {
                newRotation = wrapCounterClockwise(newRotation);
            }
            if (rotation < desiredRotation && newRotation > desiredRotation) {
                newRotation = desiredRotation;
            }
        } else {
            newRotation = rotation - maxChangeAmount;
            if (newRotation < 0) {
                newRotation = wrapClockwise(newRotation);
            }
            if (rotation > desiredRotation && newRotation < desiredRotation) {
                newRotation = desiredRotation;
            }
        }
        setRotation(newRotation);
    }
}
