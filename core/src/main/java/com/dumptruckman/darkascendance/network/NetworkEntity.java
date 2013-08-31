package com.dumptruckman.darkascendance.network;

import com.dumptruckman.darkascendance.core.Entity;
import recs.Component;

public class NetworkEntity {

    private int entityId;
    private Component[] components;

    private NetworkEntity() { }

    public NetworkEntity(Entity entity) {
        components = entity.getComponents();
        entityId = entity.getId();
    }

    public int getEntityId() {
        return entityId;
    }

    public Entity addComponentsToEntity(Entity entity) {
        entity.addComponent(components);
        return entity;
    }
}
