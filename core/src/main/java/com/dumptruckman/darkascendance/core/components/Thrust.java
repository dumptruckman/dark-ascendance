package com.dumptruckman.darkascendance.core.components;

import com.artemis.Component;

public class Thrust extends Component {
    private float currentThrust = 0f;
    private float thrustAmount = 80f;
    private float reverseThrustAmount = 0f;

    public float getCurrentThrust() {
        return currentThrust;
    }

    public Thrust setThrustPercent(float percent) {
        if (percent >= 0) {
            currentThrust = thrustAmount * percent;
        } else {
            currentThrust = reverseThrustAmount * percent;
        }
        return this;
    }

    public Thrust setThrustAmount(float accelerationAmount) {
        this.thrustAmount = accelerationAmount;
        return this;
    }

    public Thrust setReverseThrustAmount(float accelerationAmount) {
        this.reverseThrustAmount = accelerationAmount;
        return this;
    }
}
