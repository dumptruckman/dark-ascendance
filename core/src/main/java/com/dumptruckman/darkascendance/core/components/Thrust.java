package com.dumptruckman.darkascendance.core.components;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;

public class Thrust extends Component {
    private float thrust = 0f;
    private float acceleration = 80f;
    private float reverseAcceleration = 0f;

    public float getThrust() {
        return thrust;
    }

    public Thrust setThrustPercent(float percent) {
        thrust = acceleration * percent;
        return this;
    }

    // TODO These acceleration methods need better names.
    public Thrust setAccelerationAmount(float accelerationAmount) {
        this.acceleration = accelerationAmount;
        return this;
    }

    public Thrust setReverseAccelerationAmount(float accelerationAmount) {
        this.reverseAcceleration = accelerationAmount;
        return this;
    }
}
