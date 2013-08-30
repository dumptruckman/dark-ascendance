package com.dumptruckman.darkascendance.network.client;

import com.dumptruckman.darkascendance.network.KryoNetwork;
import com.dumptruckman.darkascendance.network.NetworkEntity;
import com.dumptruckman.darkascendance.network.messages.EntityMessage;
import com.dumptruckman.darkascendance.network.messages.Message;
import com.dumptruckman.darkascendance.util.GameSettings;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class GameClient extends KryoNetwork implements Observer {

    private int tcpPort;
    private Client client;
    private ClientLogicLoop clientLogicLoop;

    public GameClient(GameSettings gameSettings, int tcpPort) {
        this.tcpPort = tcpPort;
        client = new Client();
        client.addListener(this);
        clientLogicLoop = new ClientLogicLoop(gameSettings.getScreenWidth(), gameSettings.getScreenHeight());
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
    public void update(final Observable o, final Object arg) {
    }

    private void sendMessage(Object message) {
    }

    @Override
    public void received(final Connection connection, final Object o) {
        if (o instanceof Message) {
            Message message = (Message) o;
            switch (message.getMessageType()) {
                case CREATE_PLAYER_SHIP:
                    EntityMessage entityMessage = (EntityMessage) message;
                    getScreen().addPlayerShipToWorld(entityMessage.getNetworkEntity());
                    break;
            }
        }
    }
}
