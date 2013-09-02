package com.dumptruckman.darkascendance.shared.messages;

import com.dumptruckman.darkascendance.shared.Entity;
import com.dumptruckman.darkascendance.shared.network.NetworkEntity;

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
