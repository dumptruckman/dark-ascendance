package com.dumptruckman.darkascendance.network.server;

import com.dumptruckman.darkascendance.network.Test;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

public class TestHandler extends Listener {

    @Override
    public void received(final Connection connection, final Object o) {
        System.out.println(((Test) o).getMessage());
    }
}
