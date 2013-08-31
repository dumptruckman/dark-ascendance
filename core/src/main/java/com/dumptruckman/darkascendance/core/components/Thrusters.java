package com.dumptruckman.darkascendance.core.components;

import recs.Component;

public class Thrusters extends Component {

    private float thrustLevel = 0f;
    private float accelerationFactor = 80f;

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
}
