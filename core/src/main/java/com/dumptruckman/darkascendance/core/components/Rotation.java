package com.dumptruckman.darkascendance.core.components;

import com.artemis.Component;

public class Rotation  extends Component {
    public float r = 0F;

    public void setRotation(float r) {
        if (r >= 360F) {
            r = (360F * (360F % r));
        }
        if (r < 0F) {
            r = 360F - (360F * (360F % r));
        }
    }
}
