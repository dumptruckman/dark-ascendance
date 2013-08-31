package com.dumptruckman.darkascendance.shared;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectSet;
import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.shared.components.Position;
import com.dumptruckman.darkascendance.shared.components.Thrusters;
import com.dumptruckman.darkascendance.shared.components.Velocity;
import com.dumptruckman.darkascendance.shared.messages.ComponentMessage;
import com.dumptruckman.darkascendance.shared.messages.EntityMessage;
import com.dumptruckman.darkascendance.shared.messages.Message;
import com.dumptruckman.darkascendance.shared.messages.MessageType;
import com.dumptruckman.darkascendance.shared.messages.SnapshotMessage;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import recs.Component;

import java.util.Observable;
import java.util.Observer;

public abstract class KryoNetwork extends Listener implements Observer {

    protected void initializeSerializables(Kryo kryo) {
        kryo.register(Component.class);
        kryo.register(Component[].class);
        kryo.register(Object[].class);
        kryo.register(ObjectSet.class);

        kryo.register(Message.class);
        kryo.register(NetworkEntity.class);
        kryo.register(MessageType.class);
        kryo.register(EntityMessage.class);
        kryo.register(ComponentMessage.class);
        kryo.register(SnapshotMessage.class);

        kryo.register(Controls.class);
        kryo.register(Position.class);
        kryo.register(Thrusters.class);
        kryo.register(Velocity.class);
        kryo.register(Vector2.class);
    }

    public abstract void sendMessage(Message message);

    @Override
    public final void update(final Observable o, final Object arg) {
        if (arg instanceof Message) {
            Message message = (Message) arg;
            sendMessage(message);
        }
    }

    @Override
    public final void received(final Connection connection, final Object o) {
        if (o instanceof Message) {
            int latency = connection.getReturnTripTime();
            handleMessage(connection.getID(), (Message) o, latency);
        }
    }

    public abstract void handleMessage(int connectionId, Message message, final int latency);
}
