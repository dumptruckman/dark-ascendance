package com.dumptruckman.darkascendance.network.components;

import com.artemis.Component;

public class Identifier extends Component {

    private int serverEntityId;

    public Identifier(int serverEntityId) {
        this.serverEntityId = serverEntityId;
    }

    public int getServerEntityId() {
        return serverEntityId;
    }
}
