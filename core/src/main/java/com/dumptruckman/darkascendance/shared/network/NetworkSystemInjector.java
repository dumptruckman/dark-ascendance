package com.dumptruckman.darkascendance.shared.network;

import com.badlogic.gdx.utils.ObjectSet;
import recs.EntitySystem;
import recs.EntityWorld;

public class NetworkSystemInjector {

    private ObjectSet<EntitySystem> systems = new ObjectSet<EntitySystem>();
    private ObjectSet<EntitySystem> highPrioritySystems = new ObjectSet<EntitySystem>();

    NetworkSystemInjector() { }

    public void addSystem(EntitySystem system) {
        systems.add(system);
    }

    public void addHighPrioritySystem(EntitySystem system) {
        highPrioritySystems.add(system);
    }

    public EntityWorld addSystemsToWorld(EntityWorld world) {
        for (EntitySystem system : systems) {
            world.addSystem(system);
        }
        return world;
    }

    public EntityWorld addHighPrioritySystemsToWorld(EntityWorld world) {
        for (EntitySystem system : highPrioritySystems) {
            world.addSystem(system);
        }
        return world;
    }
}
