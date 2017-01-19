package com.dumptruckman.darkascendance.javaapplet;

import com.badlogic.gdx.backends.lwjgl.LwjglApplet;

import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.dumptruckman.darkascendance.shared.DarkAscendance;
import com.dumptruckman.darkascendance.client.GameSettings;

public class DarkAscendanceApplet extends LwjglApplet {

    public DarkAscendanceApplet() {
        super(new DarkAscendance(new GameSettings(640, 480), "gnarbros.dyndns.org", 25560, 25565), new LwjglApplicationConfiguration());
    }
}
