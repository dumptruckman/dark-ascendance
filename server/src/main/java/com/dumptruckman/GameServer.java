package com.dumptruckman;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class GameServer extends Listener {

    public static void main(String[] args) {
        GameServer server = new GameServer();
        try {
            server.start(25565, 25566);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private final Server server;

    private GameServer() {
        server = new Server();
        server.addListener(this);
    }

    private void start(int tcpPort, int udpPort) throws IOException {
        server.start();
        server.bind(tcpPort, udpPort);
    }

    @Override
    public void received(Connection connection, Object object) {

    }
}
