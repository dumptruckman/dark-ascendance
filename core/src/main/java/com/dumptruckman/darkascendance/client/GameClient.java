package com.dumptruckman.darkascendance.client;

import com.dumptruckman.darkascendance.client.systems.SnapshotProcessingSystem;
import com.dumptruckman.darkascendance.shared.messages.Acknowledgement;
import com.dumptruckman.darkascendance.shared.messages.MessageFactory;
import com.dumptruckman.darkascendance.shared.network.KryoNetwork;
import com.dumptruckman.darkascendance.shared.messages.EntityMessage;
import com.dumptruckman.darkascendance.shared.messages.Message;
import com.dumptruckman.darkascendance.shared.messages.SnapshotMessage;
import com.dumptruckman.darkascendance.shared.network.NetworkSystemInjector;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class GameClient extends KryoNetwork implements Observer {

    private String host;
    private int tcpPort;
    private int udpPort;
    private Client client;
    private ClientLogicLoop clientLogicLoop;

    public GameClient(GameSettings gameSettings, String host, int tcpPort, int udpPort) {
        this.host = host;
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
        client = new Client();
        client.addListener(this);

        clientLogicLoop = new ClientLogicLoop(getNetworkSystemInjector(), gameSettings.getScreenWidth(), gameSettings.getScreenHeight());
        clientLogicLoop.addObserver(this);

        initializeSerializables(client.getKryo());
    }

    public void start() throws IOException {
        client.start();
        client.connect(5000, host, tcpPort, udpPort);
    }

    public ClientLogicLoop getScreen() {
        return clientLogicLoop;
    }

    @Override
    protected void sendMessageToAll(final Message message) {
        if (message.isImportant())
            System.out.println("Sending " + message + " to server.");
        client.sendUDP(message);
    }

    @Override
    protected void sendMessage(final int connectionId, final Message message) {
        sendMessageToAll(message);
    }

    @Override
    public void resendMessage(final int connectionId, final Message message) {
        System.out.println("Resending " + message + " to server.");
        client.sendUDP(message);
    }

    @Override
    public void sendAcknowledgement(final int connectionId, Acknowledgement acknowledgement) {
        System.out.println("Sending " + acknowledgement + " to server.");
        client.sendUDP(acknowledgement);
    }

    @Override
    public void handleMessage(Message message) {
        if (message.isForAllButOneConnections() && message.getConnectionId() == client.getID()) {
            return;
        }
        if (!message.isForAllConnections() && message.getConnectionId() != client.getID()) {
            return;
        }
        EntityMessage entityMessage;
        switch (message.getMessageType()) {
            case CREATE_PLAYER_SHIP:
                System.out.println("Creating ship");
                entityMessage = (EntityMessage) message;
                if (entityMessage.getConnectionId() == client.getID()) {
                    getScreen().addPlayerShipToWorld(entityMessage.getNetworkEntity());
                } else {
                    getScreen().addOtherPlayerShipToWorld(entityMessage.getNetworkEntity());
                }
                break;
            case CREATE_ENTITY:
                entityMessage = (EntityMessage) message;
                getScreen().addOtherPlayerShipToWorld(entityMessage.getNetworkEntity());
                break;
            case DESTROY_ENTITY:
                entityMessage = (EntityMessage) message;
                getScreen().removeEntityFromWorld(entityMessage.getNetworkEntity());
                break;
            case SNAPSHOT:
                SnapshotProcessingSystem.addSnapshot((SnapshotMessage) message);
                break;
        }
    }
}
