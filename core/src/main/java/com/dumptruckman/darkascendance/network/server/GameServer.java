package com.dumptruckman.darkascendance.network.server;

import com.dumptruckman.darkascendance.network.KryoNetwork;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class GameServer extends KryoNetwork {

    private int tcpPort;
    private Server server;
    private ServerLogicLoop serverLogicLoop;

    public GameServer(int tcpPort) {
        this.tcpPort = tcpPort;
        this.server = new Server();
        this.serverLogicLoop = new ServerLogicLoop();
        initializeSerializables(server.getKryo());
        server.addListener(new TestHandler());
    }

    public void start() throws IOException {
        startServerLogic();
        server.start();
        server.bind(tcpPort);
    }

    public void startServerLogic() {
        serverLogicLoop.start();
        while (!serverLogicLoop.isReadyForNetworking()) { }
    }
}
