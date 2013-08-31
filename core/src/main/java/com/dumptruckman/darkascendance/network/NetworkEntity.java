package com.dumptruckman.darkascendance.network;

import recs.Entity;

public class NetworkEntity {

    private Object[] components;

    private NetworkEntity() { }

    public NetworkEntity(Entity entity) {
        components = entity.getComponents();
    }

    public Entity addComponentsToEntity(Entity entity) {
        for (Object component : components) {
            entity.addComponent(component);
        }
        return entity;
    }
}
