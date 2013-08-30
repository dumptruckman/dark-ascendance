package com.dumptruckman.darkascendance.network;

import com.artemis.Component;
import com.artemis.Entity;
import com.artemis.utils.Bag;
import com.artemis.utils.ImmutableBag;

public class NetworkEntity {

    private ImmutableBag<Component> components;

    private NetworkEntity() { }

    public NetworkEntity(Entity entity) {
        components = entity.getComponents(new Bag<Component>());
    }

    public Entity addComponentsToEntity(Entity entity) {
        for (int i = 0; i < components.size(); i++) {
            entity.addComponent(components.get(i));
        }
        return entity;
    }
}
