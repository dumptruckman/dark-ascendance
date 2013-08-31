package com.dumptruckman.darkascendance.network.components;

public class Identifier {

    private int serverEntityId;

    public Identifier(int serverEntityId) {
        this.serverEntityId = serverEntityId;
    }

    public int getServerEntityId() {
        return serverEntityId;
    }
}
