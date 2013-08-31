package com.dumptruckman.darkascendance.network.client;

import com.dumptruckman.darkascendance.network.KryoNetwork;
import com.dumptruckman.darkascendance.network.messages.EntityMessage;
import com.dumptruckman.darkascendance.network.messages.Message;
import com.dumptruckman.darkascendance.network.client.systems.CommandSendSystem;
import com.dumptruckman.darkascendance.network.NetworkSystemInjector;
import com.dumptruckman.darkascendance.util.GameSettings;
import com.esotericsoftware.kryonet.Client;

import java.io.IOException;
import java.util.Observer;

public class GameClient extends KryoNetwork implements Observer {

    private static final float COMMAND_RATE = 0.050F;

    private int tcpPort;
    private Client client;
    private ClientLogicLoop clientLogicLoop;

    public GameClient(GameSettings gameSettings, int tcpPort) {
        this.tcpPort = tcpPort;
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
        client.connect(5000, "127.0.0.1", tcpPort);
    }

    public ClientLogicLoop getScreen() {
        return clientLogicLoop;
    }

    @Override
    public void sendMessage(final int connectionId, final Message message) {
        client.sendTCP(message);
    }

    @Override
    public void handleMessage(int connectionId, Message message) {
        switch (message.getMessageType()) {
            case CREATE_PLAYER_SHIP:
                EntityMessage entityMessage = (EntityMessage) message;
                getScreen().addPlayerShipToWorld(entityMessage.getNetworkEntity());
                break;
        }
    }
}
