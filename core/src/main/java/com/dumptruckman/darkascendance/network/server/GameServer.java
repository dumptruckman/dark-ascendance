package com.dumptruckman.darkascendance.network.server;

import com.dumptruckman.darkascendance.network.KryoNetwork;
import com.dumptruckman.darkascendance.network.messages.Message;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class GameServer extends KryoNetwork implements Observer {

    private int tcpPort;
    private Server server;
    private ServerLogicLoop serverLogicLoop;

    public GameServer(int tcpPort) {
        this.tcpPort = tcpPort;
        this.server = new Server();
        this.serverLogicLoop = new ServerLogicLoop();
        serverLogicLoop.addObserver(this);
        initializeSerializables(server.getKryo());
        server.addListener(this);
    }

    public void start() throws IOException {
        startServerLogic();
        server.start();
        server.bind(tcpPort);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Message) {
            Message message = (Message) arg;
            server.sendToTCP(message.getConnectionId(), message);
            System.out.println("sent create ship message to " + message.getConnectionId());
        }
    }

    public void startServerLogic() {
        new Thread(serverLogicLoop).start();
        while (!serverLogicLoop.isReadyForNetworking()) { }
    }

    @Override
    public void connected(final Connection connection) {
        System.out.println("Player connected: " + connection);
        connection.sendTCP("Hey");
        serverLogicLoop.playerConnected(connection.getID());
    }

    @Override
    public void received(final Connection connection, final Object o) {
        //System.out.println(((Test) o).getMessage());
    }
}
