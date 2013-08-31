package com.dumptruckman.darkascendance.network;

import recs.Component;
import recs.Entity;

public class NetworkEntity {

    private Component[] components;

    private NetworkEntity() { }

    public NetworkEntity(Entity entity) {
        components = entity.getComponents();
    }

    public Entity addComponentsToEntity(Entity entity) {
        entity.addComponent(components);
        return entity;
    }
}
