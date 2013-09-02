package com.dumptruckman.darkascendance.server;

import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.shared.network.KryoNetwork;
import com.dumptruckman.darkascendance.shared.messages.ComponentMessage;
import com.dumptruckman.darkascendance.shared.messages.Message;
import com.dumptruckman.darkascendance.shared.network.NetworkSystemInjector;
import com.dumptruckman.darkascendance.server.systems.SnapshotCreationSystem;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class GameServer extends KryoNetwork {

    public static void main(String[] args) {
        try {
            new GameServer(25565, 27000).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static final float SNAPSHOT_RATE = .05F;

    private int tcpPort;
    private int udpPort;
    private Server server;
    private ServerLogicLoop serverLogicLoop;

    public GameServer(int tcpPort, int udpPort) {
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
        this.server = new Server();

        NetworkSystemInjector networkSystemInjector = new NetworkSystemInjector(this);
        networkSystemInjector.addSystem(new SnapshotCreationSystem(this, SNAPSHOT_RATE));
        this.serverLogicLoop = new ServerLogicLoop(networkSystemInjector);

        serverLogicLoop.addObserver(this);
        initializeSerializables(server.getKryo());
        server.addListener(this);
    }

    public void start() throws IOException {
        startServerLogic();
        server.start();
        server.bind(tcpPort, udpPort);
    }

    @Override
    public void sendMessage(Message message) {
        message.time(getCurrentGameTime());
        if (message.isUdp()) {
            sendUdpMessage(message);
        } else {
            sendTcpMessage(message);
        }
    }

    private void sendUdpMessage(Message message) {
        server.sendToAllUDP(message);
        /*
        if (message.isForAllConnections()) {
            if (message.isForAllButOneConnections()) {
                server.sendToAllExceptUDP(message.getConnectionId(), message);
            } else {
                server.sendToAllUDP(message);
            }
        } else {
            server.sendToUDP(message.getConnectionId(), message);
        }
        */
    }

    private void sendTcpMessage(final Message message) {
        server.sendToAllTCP(message);
        /*
        if (message.isForAllConnections()) {
            if (message.isForAllButOneConnections()) {
                server.sendToAllExceptTCP(message.getConnectionId(), message);
            } else {
                server.sendToAllTCP(message);
            }
        } else {
            server.sendToTCP(message.getConnectionId(), message);
        }
        */
    }

    public void startServerLogic() {
        new Thread(serverLogicLoop).start();
        while (!serverLogicLoop.isReadyForNetworking()) { }
    }

    @Override
    public void connected(final Connection connection) {
        serverLogicLoop.playerConnected(connection.getID());
    }

    @Override
    public void disconnected(final Connection connection) {
        serverLogicLoop.playerDisconnected(connection.getID());
    }

    @Override
    public void handleMessage(Message message, final int latency) {
        switch (message.getMessageType()) {
            case PLAYER_INPUT_STATE:
                ComponentMessage entityMessage = (ComponentMessage) message;
                serverLogicLoop.updatePlayerControls((Controls) entityMessage.getComponent());
                break;
        }
    }
}
