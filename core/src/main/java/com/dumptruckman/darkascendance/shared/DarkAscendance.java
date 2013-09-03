package com.dumptruckman.darkascendance.shared;

import com.badlogic.gdx.Game;
import com.dumptruckman.darkascendance.client.GameClient;
import com.dumptruckman.darkascendance.client.GameSettings;

import java.io.IOException;

public class DarkAscendance extends Game {

    private final GameSettings gameSettings;
    private String host;
    private int tcpPort;
    private int udpPort;

    public DarkAscendance(GameSettings gameSettings, String host, int tcpPort, int udpPort) {
        this.gameSettings = gameSettings;
        this.host = host;
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
    }

    @Override
    public void create() {
        GameClient gameClient = new GameClient(gameSettings, host, tcpPort, udpPort);
        try {
            gameClient.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setScreen(gameClient.getScreen());
    }
}
