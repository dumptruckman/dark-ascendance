package com.dumptruckman.darkascendance.network.client;

import com.dumptruckman.darkascendance.network.KryoNetwork;
import com.dumptruckman.darkascendance.network.client.systems.SnapshotApplicationSystem;
import com.dumptruckman.darkascendance.network.messages.EntityMessage;
import com.dumptruckman.darkascendance.network.messages.Message;
import com.dumptruckman.darkascendance.network.client.systems.CommandSendSystem;
import com.dumptruckman.darkascendance.network.NetworkSystemInjector;
import com.dumptruckman.darkascendance.network.messages.SnapshotMessage;
import com.dumptruckman.darkascendance.util.GameSettings;
import com.esotericsoftware.kryonet.Client;
import recs.Entity;

import java.io.IOException;
import java.util.Observer;
import java.util.concurrent.ConcurrentHashMap;

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
        client.connect(5000, "75.143.227.173", tcpPort);
    }

    public ClientLogicLoop getScreen() {
        return clientLogicLoop;
    }

    @Override
    public void sendMessage(final Message message) {
        client.sendTCP(message);
    }

    @Override
    public void handleMessage(int connectionId, Message message, final int latency) {
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
                SnapshotApplicationSystem.addSnapshot((SnapshotMessage) message);
                break;
        }
    }
}
