package com.dumptruckman.darkascendance.network.client;

import com.dumptruckman.darkascendance.network.KryoNetwork;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;

public class GameClient extends KryoNetwork {

    private int tcpPort;
    private Client client;

    public GameClient(int tcpPort) {
        this.tcpPort = tcpPort;
        this.client = new Client();
        initializeSerializables(client.getKryo());
    }

    public void start() throws IOException {
        client.start();
        client.connect(5000, "127.0.0.1", tcpPort);
    }

    public void sendMessage(Object message) {
        client.sendTCP(message);
    }
}
