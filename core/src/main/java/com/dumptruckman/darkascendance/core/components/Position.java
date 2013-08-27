package com.dumptruckman.darkascendance.core.components;

import com.artemis.Component;

public class Position extends Component {
    public float x = 0F;
    public float y = 0F;
    public float r = 0F;

    public void setRotation(float r) {
        if (r >= 360F) {
            r = (r % 360F) / 360F;
        } else if (r < 0F) {
            r = 360 - (-r % 360F);
        }
        this.r = r;
    }
}
