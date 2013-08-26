package com.dumptruckman.darkascendance.util;

public class GameSettings {
    private float screenWidth;
    private float screenHeight;

    public GameSettings(float screenWidth, float screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public float getScreenHeight() {
        return screenHeight;
    }
}
