package com.dumptruckman.darkascendance.server;

import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.shared.messages.Acknowledgement;
import com.dumptruckman.darkascendance.shared.messages.MessageFactory;
import com.dumptruckman.darkascendance.shared.network.KryoNetwork;
import com.dumptruckman.darkascendance.shared.messages.ComponentMessage;
import com.dumptruckman.darkascendance.shared.messages.Message;
import com.dumptruckman.darkascendance.shared.network.NetworkSystemInjector;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Server;

import java.io.IOException;

public class GameServer extends KryoNetwork {

    public static void main(String[] args) {
        int tcpPort = 25565;
        int udpPort = 25566;

        if (args.length > 1) {
            try {
                tcpPort = Integer.parseInt(args[0]);
                udpPort = Integer.parseInt(args[1]);
            } catch (NumberFormatException ignore) { }
        }
        try {
            new GameServer(tcpPort, udpPort).start();
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

        this.serverLogicLoop = new ServerLogicLoop(getNetworkSystemInjector());
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
        if (message.isImportant())
            System.out.println("Sending " + message + " to all clients.");
        server.sendToAllUDP(message);
    }

    @Override
    public void resendMessage(final int connectionId, final Message message) {
        System.out.println("Resending " + message + " to connection " + connectionId);
        server.sendToUDP(connectionId, message);
    }

    @Override
    public void sendAcknowledgement(int connectionId, Acknowledgement acknowledgement) {
        System.out.println("Sending " + acknowledgement + " to connection " + connectionId);
        server.sendToUDP(connectionId, acknowledgement);
    }

    public void startServerLogic() {
        new Thread(serverLogicLoop).start();
        while (!serverLogicLoop.isReadyForNetworking()) { }
    }

    @Override
    public void handleMessage(Message message) {
        switch (message.getMessageType()) {
            case PLAYER_CONNECTED:
                serverLogicLoop.playerConnected(message.getConnectionId());
                break;
            case PLAYER_DISCONNECTED:
                serverLogicLoop.playerDisconnected(message.getConnectionId());
                break;
            case PLAYER_INPUT_STATE:
                ComponentMessage entityMessage = (ComponentMessage) message;
                serverLogicLoop.updatePlayerControls((Controls) entityMessage.getComponent());
                break;
        }
    }
}
