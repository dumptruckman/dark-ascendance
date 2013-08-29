package com.dumptruckman.darkascendance.core;

import com.badlogic.gdx.Game;
import com.dumptruckman.darkascendance.network.server.GameServer;
import com.dumptruckman.darkascendance.util.GameSettings;

import java.io.IOException;

public class DarkAscendance extends Game {

    private final GameSettings gameSettings;

    public DarkAscendance(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    @Override
    public void create() {
        try {
            new GameServer(8080).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        GameScreen gameScreen = new GameScreen(gameSettings.getScreenWidth(), gameSettings.getScreenHeight());
        setScreen(gameScreen);
    }
}
