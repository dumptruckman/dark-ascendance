package com.dumptruckman.darkascendance.core.components;

import com.artemis.Component;

public class Position extends Component {
    public float x = 0F;
    public float y = 0F;
    public float r = 0F;

    public void setRotation(float r) {
        if (r >= 360F) {
            r = wrapCounterClockwise(r);
        } else if (r < 0F) {
            r = wrapClockwise(r);
        }
        this.r = r;
    }

    private float wrapCounterClockwise(float r) {
        return (r % 360F) / 360F;
    }

    private float wrapClockwise(float r) {
        return 360 - (-r % 360F);
    }

    public void attainRotation(float desiredRotation, float maxChangeAmount) {
        if (r == desiredRotation) {
            return;
        }
        System.out.println("Want to get " + desiredRotation + " from " + r + " with " + maxChangeAmount);
        float direction = desiredRotation - r;
        if (direction < -180)  {
            direction += 360;
        }
        if (direction > 180) {
            direction -= 360;
        }

        float newRotation;
        if (direction > 0) {
            newRotation = r + maxChangeAmount;
            if (newRotation >= 360) {
                newRotation = wrapCounterClockwise(newRotation);
            }
            System.out.println("adding " + maxChangeAmount + " to make " + newRotation);
            if (r < desiredRotation && newRotation > desiredRotation) {
                newRotation = desiredRotation;
            }
        } else {
            newRotation = r - maxChangeAmount;
            if (newRotation < 0) {
                newRotation = wrapClockwise(newRotation);
            }
            System.out.println("subtracting " + maxChangeAmount + " to make " + newRotation);
            if (r > desiredRotation && newRotation < desiredRotation) {
                newRotation = desiredRotation;
            }
        }
        setRotation(newRotation);
    }
}
