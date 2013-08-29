package com.dumptruckman.darkascendance.network.client;

import com.dumptruckman.darkascendance.network.KryoNetwork;
import com.dumptruckman.darkascendance.util.GameSettings;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class GameClient extends KryoNetwork implements Observer {

    private int tcpPort;
    private Client client;
    private ClientLogicLoop clientLogicLoop;

    public GameClient(GameSettings gameSettings, int tcpPort) {
        this.tcpPort = tcpPort;
        client = new Client();
        clientLogicLoop = new ClientLogicLoop(gameSettings.getScreenWidth(), gameSettings.getScreenHeight());
        clientLogicLoop.addObserver(this);
        initializeSerializables(client.getKryo());
    }

    public void start() throws IOException {
        client.start();
        client.connect(5000, "127.0.0.1", tcpPort);
    }

    public ClientLogicLoop getScreen() {
        return clientLogicLoop;
    }

    @Override
    public void update(final Observable o, final Object arg) {
        sendMessage(arg);
    }

    private void sendMessage(Object message) {
        client.sendTCP(message);
    }
}
