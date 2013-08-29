package com.dumptruckman.darkascendance.core;

import com.badlogic.gdx.Game;
import com.dumptruckman.darkascendance.network.server.GameServer;
import com.dumptruckman.darkascendance.util.GameSettings;

public class DarkAscendance extends Game {

    private final GameSettings gameSettings;

    public DarkAscendance(GameSettings gameSettings) {
        this.gameSettings = gameSettings;
    }

    @Override
    public void create() {
        new GameServer(8080).start();
        GameScreen gameScreen = new GameScreen(gameSettings.getScreenWidth(), gameSettings.getScreenHeight());
        setScreen(gameScreen);
    }
}
