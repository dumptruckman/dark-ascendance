package com.dumptruckman.darkascendance.network;

import com.artemis.Component;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Vector2;
import com.dumptruckman.darkascendance.core.components.Controls;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Thrusters;
import com.dumptruckman.darkascendance.core.components.Velocity;
import com.dumptruckman.darkascendance.network.messages.ComponentMessage;
import com.dumptruckman.darkascendance.network.messages.EntityMessage;
import com.dumptruckman.darkascendance.network.messages.Message;
import com.dumptruckman.darkascendance.network.messages.MessageType;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

import java.util.Observable;
import java.util.Observer;

public abstract class KryoNetwork extends Listener implements Observer {

    protected void initializeSerializables(Kryo kryo) {
        kryo.register(Bag.class);
        kryo.register(ImmutableBag.class);
        kryo.register(Object[].class);

        kryo.register(Message.class);
        kryo.register(NetworkEntity.class);
        kryo.register(MessageType.class);
        kryo.register(EntityMessage.class);
        kryo.register(ComponentMessage.class);

        kryo.register(Component.class);
        kryo.register(Controls.class);
        kryo.register(Position.class);
        kryo.register(Thrusters.class);
        kryo.register(Velocity.class);
        kryo.register(Vector2.class);
    }

    public abstract void sendMessage(int connectionId, Message message);

    @Override
    public final void update(final Observable o, final Object arg) {
        if (arg instanceof Message) {
            Message message = (Message) arg;
            sendMessage(message.getConnectionId(), message);
        }
    }

    @Override
    public final void received(final Connection connection, final Object o) {
        if (o instanceof Message) {
            handleMessage(connection.getID(), (Message) o);
        }
    }

    public abstract void handleMessage(int connectionId, Message message);
}
