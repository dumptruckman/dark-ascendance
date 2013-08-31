package com.dumptruckman.darkascendance.core.components;

public class Thrusters extends Component implements Cloneable {

    private float thrustLevel = 0f;
    private float accelerationFactor = 80f;

    public void copyState(Component component) {
        if (component instanceof Thrusters) {
            copyState((Thrusters) component);
        }
    }

    public void copyState(Thrusters thrusters) {
        this.thrustLevel = thrusters.thrustLevel;
        this.accelerationFactor = thrusters.accelerationFactor;
    }

    public float getAccelerationAddedByThrust() {
        return thrustLevel * accelerationFactor;
    }

    public float getThrustLevel() {
        return thrustLevel;
    }

    public Thrusters setThrustLevel(float thrustPercent) {
        thrustLevel = thrustPercent;
        return this;
    }

    public float getAccelerationFactor() {
        return accelerationFactor;
    }

    public Thrusters setAccelerationFactor(float accelerationAmount) {
        this.accelerationFactor = accelerationAmount;
        return this;
    }

    @Override
    public Thrusters clone() {
        return (Thrusters) super.clone();
    }
}
