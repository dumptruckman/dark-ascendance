package com.dumptruckman.darkascendance.network.client.systems;

import com.dumptruckman.darkascendance.core.Entity;
import recs.EntitySystem;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ServerEntityHandlingSystem extends EntitySystem {

    private static Queue<Entity> entitiesToAdd = new ConcurrentLinkedQueue<Entity>();
    private static Queue<Entity> entitiesToRemove = new ConcurrentLinkedQueue<Entity>();

    public static void addEntity(Entity entity) {
        entitiesToAdd.add(entity);
    }

    public static void removeEntity(Entity entity) {
        entitiesToRemove.add(entity);
    }

    @Override
    protected void processSystem(final float deltaInSec) {
        Entity entity;
        while ((entity = entitiesToAdd.poll()) != null) {
            world.addEntity(entity);
            System.out.println("added entity to world: " + entity);
        }
        while ((entity = entitiesToRemove.poll()) != null) {
            world.removeEntity(entity.getId());
        }
    }
}
