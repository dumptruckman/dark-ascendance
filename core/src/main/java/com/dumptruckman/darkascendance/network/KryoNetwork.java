package com.dumptruckman.darkascendance.network;

import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;
import com.badlogic.gdx.math.Vector2;
import com.dumptruckman.darkascendance.core.components.Controls;
import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Thrusters;
import com.dumptruckman.darkascendance.core.components.Velocity;
import com.dumptruckman.darkascendance.network.messages.EntityMessage;
import com.dumptruckman.darkascendance.network.messages.Message;
import com.dumptruckman.darkascendance.network.messages.MessageType;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Listener;

public class KryoNetwork extends Listener {

    protected void initializeSerializables(Kryo kryo) {
        kryo.register(Bag.class);
        kryo.register(ImmutableBag.class);
        kryo.register(Object[].class);

        kryo.register(Message.class);
        kryo.register(NetworkEntity.class);
        kryo.register(MessageType.class);
        kryo.register(EntityMessage.class);

        kryo.register(Controls.class);
        kryo.register(Position.class);
        kryo.register(Thrusters.class);
        kryo.register(Velocity.class);
        kryo.register(Vector2.class);
    }
}
