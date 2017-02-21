package com.dumptruckman.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dumptruckman.DarkAscendanceGame;

public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.vSyncEnabled = false;
        config.foregroundFPS = 3000;
        config.backgroundFPS = 3000;
        new LwjglApplication(new DarkAscendanceGame(config.width, config.height), config);
    }
}
