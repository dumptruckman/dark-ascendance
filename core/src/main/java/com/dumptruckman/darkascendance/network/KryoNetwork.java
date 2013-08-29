package com.dumptruckman.darkascendance.network;

import com.dumptruckman.darkascendance.core.components.Position;
import com.dumptruckman.darkascendance.core.components.Thrusters;
import com.dumptruckman.darkascendance.core.components.Velocity;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Listener;

public class KryoNetwork extends Listener {

    protected void initializeSerializables(Kryo kryo) {
        kryo.register(Position.class);
        kryo.register(Thrusters.class);
        kryo.register(Velocity.class);
    }
}
