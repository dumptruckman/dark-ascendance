package com.dumptruckman.darkascendance.network.server;

import com.dumptruckman.darkascendance.core.components.Controls;
import com.dumptruckman.darkascendance.network.KryoNetwork;
import com.dumptruckman.darkascendance.network.messages.ComponentMessage;
import com.dumptruckman.darkascendance.network.messages.EntityMessage;
import com.dumptruckman.darkascendance.network.messages.Message;
import com.dumptruckman.darkascendance.network.systems.NetworkSystemInjector;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class GameServer extends KryoNetwork {

    private int tcpPort;
    private Server server;
    private ServerLogicLoop serverLogicLoop;

    public GameServer(int tcpPort) {
        this.tcpPort = tcpPort;
        this.server = new Server();

        NetworkSystemInjector networkSystemInjector = new NetworkSystemInjector(this);

        this.serverLogicLoop = new ServerLogicLoop(networkSystemInjector);

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
    public void sendMessage(final int connectionId, final Message message) {
        server.sendToTCP(connectionId, message);
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
    public void handleMessage(int connectionId, Message message) {
        switch (message.getMessageType()) {
            case PLAYER_INPUT_STATE:
                ComponentMessage entityMessage = (ComponentMessage) message;
                serverLogicLoop.updatePlayerControls((Controls) entityMessage.getComponent());
                break;
        }
    }
}
