package com.dumptruckman.darkascendance.core;

import com.badlogic.gdx.Game;
import com.dumptruckman.darkascendance.network.client.GameClient;
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
        GameClient gameClient = new GameClient(gameSettings, 25565, 25562);
        try {
            gameClient.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setScreen(gameClient.getScreen());
    }
}
