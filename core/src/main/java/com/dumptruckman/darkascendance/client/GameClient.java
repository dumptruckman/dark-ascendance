package com.dumptruckman.darkascendance.client;

import com.dumptruckman.darkascendance.client.systems.SnapshotProcessingSystem;
import com.dumptruckman.darkascendance.shared.network.KryoNetwork;
import com.dumptruckman.darkascendance.shared.messages.EntityMessage;
import com.dumptruckman.darkascendance.shared.messages.Message;
import com.dumptruckman.darkascendance.client.systems.CommandSendSystem;
import com.dumptruckman.darkascendance.shared.network.NetworkSystemInjector;
import com.dumptruckman.darkascendance.shared.messages.SnapshotMessage;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.util.Observer;

public class GameClient extends KryoNetwork implements Observer {

    private static final float COMMAND_RATE = 0.050F;

    private int tcpPort;
    private int udpPort;
    private Client client;
    private ClientLogicLoop clientLogicLoop;

    public GameClient(GameSettings gameSettings, int tcpPort, int udpPort) {
        this.tcpPort = tcpPort;
        this.udpPort = udpPort;
        client = new Client();
        client.addListener(this);

        NetworkSystemInjector networkSystemInjector = new NetworkSystemInjector(this);
        networkSystemInjector.addSystem(new CommandSendSystem(this, COMMAND_RATE));
        clientLogicLoop = new ClientLogicLoop(networkSystemInjector, gameSettings.getScreenWidth(), gameSettings.getScreenHeight());
        clientLogicLoop.addObserver(this);

        initializeSerializables(client.getKryo());
    }

    public void start() throws IOException {
        client.start();
        client.connect(5000, "75.143.227.173", tcpPort, udpPort);
    }

    public ClientLogicLoop getScreen() {
        return clientLogicLoop;
    }

    @Override
    public void sendMessage(final Message message) {
        client.sendTCP(message);
    }

    @Override
    public void handleMessage(Message message, final int latency) {
        if (message.isForAllButOneConnections() && message.getConnectionId() == client.getID()) {
            return;
        }
        if (!message.isForAllConnections() && message.getConnectionId() != client.getID()) {
            return;
        }
        EntityMessage entityMessage;
        switch (message.getMessageType()) {
            case CREATE_PLAYER_SHIP:
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
