package com.dumptruckman.darkascendance.shared.network;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.IntSet;
import com.badlogic.gdx.utils.ObjectSet;
import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.shared.components.Position;
import com.dumptruckman.darkascendance.shared.components.Thrusters;
import com.dumptruckman.darkascendance.shared.components.Velocity;
import com.dumptruckman.darkascendance.shared.messages.Acknowledgement;
import com.dumptruckman.darkascendance.shared.messages.ComponentMessage;
import com.dumptruckman.darkascendance.shared.messages.EntityMessage;
import com.dumptruckman.darkascendance.shared.messages.Message;
import com.dumptruckman.darkascendance.shared.messages.MessageBase;
import com.dumptruckman.darkascendance.shared.messages.MessageFactory;
import com.dumptruckman.darkascendance.shared.messages.MessageType;
import com.dumptruckman.darkascendance.shared.messages.SnapshotMessage;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import recs.Component;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class KryoNetwork extends Listener implements Observer {

    private MessageReceiver receiver = new MessageReceiver(this);
    private MessageGuarantor messageGuarantor = new MessageGuarantor();
    private List<Integer> connections = new CopyOnWriteArrayList<Integer>();

    protected void initializeSerializables(Kryo kryo) {
        kryo.register(Component.class);
        kryo.register(Component[].class);
        kryo.register(Object[].class);
        kryo.register(ObjectSet.class);
        kryo.register(Iterable.class);
        kryo.register(ArrayList.class);

        kryo.register(Message.class);
        kryo.register(NetworkEntity.class);
        kryo.register(MessageType.class);
        kryo.register(EntityMessage.class);
        kryo.register(ComponentMessage.class);
        kryo.register(SnapshotMessage.class);
        kryo.register(Acknowledgement.class);
        kryo.register(MessageBase.class);

        kryo.register(Controls.class);
        kryo.register(Position.class);
        kryo.register(Thrusters.class);
        kryo.register(Velocity.class);
        kryo.register(Vector2.class);
    }

    protected NetworkSystemInjector getNetworkSystemInjector() {
        NetworkSystemInjector networkSystemInjector = new NetworkSystemInjector();
        networkSystemInjector.addHighPrioritySystem(new TimeKeepingSystem(this));
        networkSystemInjector.addHighPrioritySystem(new MessageProcessingSystem(this, receiver, messageGuarantor));
        return networkSystemInjector;
    }

    protected abstract void sendMessageToAll(Message message);

    protected abstract void sendMessage(int connectionId, Message message);

    protected abstract void resendMessage(int connectionId, Message message);

    protected abstract void sendAcknowledgement(int connectionId, Acknowledgement acknowledgement);

    @Override
    public final void update(final Observable o, final Object arg) {
        if (arg instanceof Message) {
            Message message = (Message) arg;
            message.time(getCurrentTime());
            if (message.isImportant()) {
                Iterator<Integer> connectionsIterator = connections.iterator();
                Integer connectionId = null;
                while (connectionsIterator.hasNext()) {
                    Message messageCopy;
                    if (connectionId == null) {
                        messageCopy = message;
                    } else {
                        messageCopy = message.clone();
                    }
                    connectionId = connectionsIterator.next();
                    messageGuarantor.guaranteeMessage(connectionId, messageCopy);
                    sendMessage(connectionId, messageCopy);
                }
            } else {
                sendMessageToAll(message);
            }
        }
    }

    @Override
    public final void received(final Connection connection, final Object o) {
        int connectionId = connection.getID();
        int latency = connection.getReturnTripTime();
        if (o instanceof Message) {
            receiver.receiveMessage(connectionId, (Message) o, latency);
        } else if (o instanceof Acknowledgement) {
            receiver.receiveAcknowledgement(connectionId, (Acknowledgement) o);
        }
    }

    @Override
    public void connected(final Connection connection) {
        int connectionId = connection.getID();
        connections.add(connectionId);
        Message playerConnectionMessage = MessageFactory.playerConnected(connectionId);
        receiver.receiveMessage(connectionId, playerConnectionMessage, connection.getReturnTripTime());
    }

    @Override
    public void disconnected(final Connection connection) {
        Integer connectionId = connection.getID();
        connections.remove(connectionId);
        Message playerConnectionMessage = MessageFactory.playerDisconnected(connectionId);
        receiver.receiveMessage(connectionId, MessageFactory.playerDisconnected(connectionId), connection.getReturnTripTime());
    }

    public abstract void handleMessage(Message message);

    public void setCurrentTime(long currentTime) {
        messageGuarantor.setCurrentTime(currentTime);
    }

    public long getCurrentTime() {
        return messageGuarantor.getCurrentTime();
    }
}
