package com.dumptruckman.darkascendance.server;

import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.shared.messages.Acknowledgement;
import com.dumptruckman.darkascendance.shared.network.KryoNetwork;
import com.dumptruckman.darkascendance.shared.messages.ComponentMessage;
import com.dumptruckman.darkascendance.shared.messages.Message;
import com.dumptruckman.darkascendance.shared.network.NetworkSystemInjector;
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

        this.serverLogicLoop = new ServerLogicLoop(new NetworkSystemInjector(this));
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
        getUdpGuarantor().guaranteeMessage(message);
        server.sendToAllUDP(message);
    }

    @Override
    public void resendMessage(final int connectionId, final Message message) {
        System.out.println("Resending message " + message.getMessageId() + " to connection " + connectionId);
        server.sendToUDP(connectionId, message);
    }

    @Override
    public void sendAcknowledgement(final int connectionId, final Acknowledgement acknowledgement) {
        System.out.println("Sending ack " + acknowledgement.getMessageId() + " to connection " + connectionId);
        server.sendToUDP(connectionId, acknowledgement);
    }

    public void startServerLogic() {
        new Thread(serverLogicLoop).start();
        while (!serverLogicLoop.isReadyForNetworking()) { }
    }

    @Override
    public void connected(final Connection connection) {
        getUdpGuarantor().addConnection(connection.getID());
        serverLogicLoop.playerConnected(connection.getID());
    }

    @Override
    public void disconnected(final Connection connection) {
        getUdpGuarantor().removeConnection(connection.getID());
        serverLogicLoop.playerDisconnected(connection.getID());
    }

    @Override
    public void handleMessage(Message message) {
        switch (message.getMessageType()) {
            case PLAYER_INPUT_STATE:
                ComponentMessage entityMessage = (ComponentMessage) message;
                serverLogicLoop.updatePlayerControls((Controls) entityMessage.getComponent());
                break;
        }
    }
}
