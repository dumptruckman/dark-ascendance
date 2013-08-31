package com.dumptruckman.darkascendance.network;

import com.dumptruckman.darkascendance.network.KryoNetwork;
import recs.EntitySystem;
import recs.EntityWorld;

import java.util.ArrayList;
import java.util.List;

public class NetworkSystemInjector {

    private KryoNetwork kryoNetwork;
    private List<EntitySystem> systems = new ArrayList<EntitySystem>();

    public NetworkSystemInjector(KryoNetwork kryoNetwork) {
        this.kryoNetwork = kryoNetwork;
    }

    public void addSystem(EntitySystem system) {
        systems.add(system);
    }

    public EntityWorld addSystemsToWorld(EntityWorld world) {
        for (EntitySystem system : systems) {
            world.addSystem(system);
        }
        return world;
    }
}
