package com.dumptruckman.darkascendance.network.messages;

import com.dumptruckman.darkascendance.core.Entity;
import com.dumptruckman.darkascendance.network.NetworkEntity;

public class EntityMessage extends Message {

    private NetworkEntity networkEntity;

    public EntityMessage entity(Entity entity) {
        this.networkEntity = new NetworkEntity(entity);
        return this;
    }

    public NetworkEntity getNetworkEntity() {
        return networkEntity;
    }
}
