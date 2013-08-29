package com.dumptruckman.darkascendance.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.dumptruckman.darkascendance.core.DarkAscendance;
import com.dumptruckman.darkascendance.util.GameSettings;

public class DarkAscendanceDesktop {
    public static void main (String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.useGL20 = true;
        config.vSyncEnabled = false;
        config.foregroundFPS = 3000;
        config.backgroundFPS = 3000;
        GameSettings gameSettings = new GameSettings(config.width, config.height);
        new LwjglApplication(new DarkAscendance(gameSettings), config);
    }
}
