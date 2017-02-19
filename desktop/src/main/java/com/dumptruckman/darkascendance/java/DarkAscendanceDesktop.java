package com.dumptruckman.darkascendance.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import com.dumptruckman.darkascendance.shared.DarkAscendance;
import com.dumptruckman.darkascendance.client.GameSettings;

public class DarkAscendanceDesktop {
    public static void main (String[] args) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.vSyncEnabled = false;
        config.foregroundFPS = 3000;
        config.backgroundFPS = 3000;
        String host = args[0];
        int tcpPort = 25565;
        int udpPort = 25566;

        if (args.length > 2) {
            host = args[0];
            try {
                tcpPort = Integer.parseInt(args[1]);
                udpPort = Integer.parseInt(args[2]);
            } catch (NumberFormatException ignore) { }
        }

        GameSettings gameSettings = new GameSettings(config.width, config.height);
        new LwjglApplication(new DarkAscendance(gameSettings, host, tcpPort, udpPort), config);
    }
} 
