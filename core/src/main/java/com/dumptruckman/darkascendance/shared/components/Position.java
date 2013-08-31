package com.dumptruckman.darkascendance.shared.components;

public class Position extends Component implements Cloneable {

    private float x = 0F;
    private float y = 0F;
    private float rotation = 0F;

    public void copyState(Component component) {
        if (component instanceof Position) {
            copyState((Position) component);
        }
    }

    public void copyState(Position position) {
        this.x = position.x;
        this.y = position.y;
        this.rotation = position.rotation;
    }

    public float getX() {
        return x;
    }

    public Position setX(float x) {
        this.x = x;
        return this;
    }

    public float getY() {
        return y;
    }

    public Position setY(float y) {
        this.y = y;
        return this;
    }

    public float getRotation() {
        return rotation;
    }

    public Position setRotation(float r) {
        if (r >= 360F) {
            r = wrapCounterClockwise(r);
        } else if (r < 0F) {
            r = wrapClockwise(r);
            if (r == 360) {
                r = 0;
            }
        }
        this.rotation = r;

        return this;
    }

    private float wrapCounterClockwise(float r) {
        return r % 360F;
    }

    private float wrapClockwise(float r) {
        return 360 - (-r % 360F);
    }

    public Position attainRotation(float desiredRotation, float maxChangeAmount) {
        if (rotation == desiredRotation) {
            return this;
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

        return this;
    }

    @Override
    public Position clone() {
        return (Position) super.clone();
    }
}
