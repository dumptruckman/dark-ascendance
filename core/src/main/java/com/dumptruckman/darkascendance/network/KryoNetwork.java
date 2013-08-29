package com.dumptruckman.darkascendance.network;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Listener;

public class KryoNetwork extends Listener {

    protected void initializeSerializables(Kryo kryo) {
        kryo.register(Test.class);
    }
}
