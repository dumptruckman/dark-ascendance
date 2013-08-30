package com.dumptruckman.darkascendance.network.systems;

import com.artemis.EntitySystem;
import com.artemis.World;
import com.dumptruckman.darkascendance.network.KryoNetwork;

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

    public World addSystemsToWorld(World world) {
        for (EntitySystem system : systems) {
            world.setSystem(system);
        }
        return world;
    }
}
