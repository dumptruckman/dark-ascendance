package com.dumptruckman.darkascendance.network.messages;

import com.dumptruckman.darkascendance.network.NetworkEntity;
import com.dumptruckman.darkascendance.recs.Entity;

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
