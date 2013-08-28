package com.dumptruckman.darkascendance.core.components;

import com.artemis.Component;
import com.badlogic.gdx.math.MathUtils;

public class Thrust extends Component {
    private float thrust = 0f;
    private float maxForwardThrust = 80f;
    private float maxReverseThrust = 0;

    public float getThrust() {
        return thrust;
    }

    public Thrust setThrust(float newThrust) {
        thrust = MathUtils.clamp(newThrust, maxReverseThrust, maxForwardThrust);
        if (newThrust > maxForwardThrust) {
            thrust = maxForwardThrust;
        } else if (newThrust < maxReverseThrust) {
            thrust = maxReverseThrust;
        } else {
            thrust = newThrust;
        }
        return this;
    }

    public Thrust setMaxForwardThrust(float maxForwardThrust) {
        this.maxForwardThrust = maxForwardThrust;
        return this;
    }

    public Thrust setMaxReverseThrust(float maxReverseThrust) {
        this.maxReverseThrust = maxReverseThrust;
        return this;
    }
}
