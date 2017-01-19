package com.dumptruckman.darkascendance.server;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.TimeUtils;
import com.dumptruckman.darkascendance.shared.Entity;
import com.dumptruckman.darkascendance.shared.GameLogic;
import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.shared.messages.MessageFactory;
import com.dumptruckman.darkascendance.shared.network.NetworkSystemInjector;
import com.dumptruckman.darkascendance.server.systems.SnapshotCreationSystem;
import com.dumptruckman.darkascendance.shared.snapshot.Snapshot;
import recs.EntityWorld;

import java.util.concurrent.ConcurrentHashMap;

public class ServerLogicLoop extends GameLogic implements Runnable {

    private static ConcurrentHashMap<Integer, Entity> connectedPlayerShips = new ConcurrentHashMap<Integer, Entity>();

    private volatile boolean readyForNetworking = false;
    private float deltaTime = 0L;
    private long lastTime = 0L;

    ServerLogicLoop(NetworkSystemInjector networkSystemInjector) {
        super(new EntityWorld(), true);
        networkSystemInjector.addHighPrioritySystemsToWorld(getWorld());
        addLogicSystems();
        getWorld().addSystem(new SnapshotCreationSystem(this));
    }

    @Override
    public void run() {
        SnapshotCreationSystem.lockToThread();
        updateTime();
        while (true) {
            updateTime();
            processGameLogic(deltaTime);

            if (!readyForNetworking) {
                readyForNetworking = true;
            }
        }
    }

    private void updateTime() {
        long time = TimeUtils.nanoTime();
        deltaTime = (time - lastTime) / 1000000000.0f;
        lastTime = time;
    }

    public boolean isReadyForNetworking() {
        return readyForNetworking;
    }

    public void playerConnected(int connectionId) {
        System.out.println("Player connected: " + connectionId);
        IntMap.Values<recs.Entity> entities = getWorld().getAddedEntities().values();
        while (entities.hasNext()) {
            Entity entity = (Entity) entities.next();
            setChanged();
            notifyObservers(MessageFactory.createEntity(connectionId, entity));
        }
        Entity entity = getEntityFactory().createBasicShip();
        setChanged();
        notifyObservers(MessageFactory.createPlayerShip(connectionId, entity));
        connectedPlayerShips.put(connectionId, entity);
    }

    public void playerDisconnected(int connectionId) {
        Entity entity = connectedPlayerShips.get(connectionId);
        if (entity != null) {
            setChanged();
            notifyObservers(MessageFactory.destroyEntity(connectionId, entity));
            getWorld().removeEntity(entity.getId());
            connectedPlayerShips.remove(connectionId);
            System.out.println("Player disconnected: " + connectionId);
        } else {
            System.out.println("Player disconnected before loading: " + connectionId);
        }
    }

    public void updatePlayerControls(Controls updatedControls) {
        recs.Entity entity = getWorld().getEntity(updatedControls.getEntityId());
        Controls oldControls = entity.getComponent(Controls.class);
        oldControls.copyState(updatedControls);
    }

    public void snapshotPrepared(Snapshot snapshot) {
        setChanged();
        notifyObservers(MessageFactory.createSnapshot(snapshot));
    }
}
