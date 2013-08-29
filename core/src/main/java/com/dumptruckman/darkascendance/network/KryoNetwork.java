package com.dumptruckman.darkascendance.network;

import com.esotericsoftware.kryo.Kryo;

public class KryoNetwork {

    protected void initializeSerializables(Kryo kryo) {
        kryo.register(Test.class);
    }
}
