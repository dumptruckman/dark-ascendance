package com.dumptruckman.darkascendance.server;

import com.badlogic.gdx.utils.IntMap;
import com.badlogic.gdx.utils.TimeUtils;
import com.dumptruckman.darkascendance.shared.Entity;
import com.dumptruckman.darkascendance.shared.GameLogic;
import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.shared.messages.MessageFactory;
import com.dumptruckman.darkascendance.shared.network.NetworkSystemInjector;
import com.dumptruckman.darkascendance.server.systems.SnapshotCreationSystem;
import recs.EntityWorld;

import java.util.concurrent.ConcurrentHashMap;

class ServerLogicLoop extends GameLogic implements Runnable {

    private static final float NANOS_IN_SECOND = 1000000000.0F;

    private static ConcurrentHashMap<Integer, Entity> connectedPlayerShips = new ConcurrentHashMap<Integer, Entity>();

    private volatile boolean readyForNetworking = false;
    private float deltaTime = 0L;
    private float lastTime = 0L;

    public ServerLogicLoop(NetworkSystemInjector networkSystemInjector) {
        super(new EntityWorld());
        networkSystemInjector.addTimeKeepingSystemToWorld(getWorld());
        addLogicSystems();
        networkSystemInjector.addSystemsToWorld(getWorld());
    }

    @Override
    public void run() {
        SnapshotCreationSystem.lockToThread();
        lastTime = TimeUtils.nanoTime();
        while (true) {
            updateTime();
            processGameLogic(deltaTime);

            if (!readyForNetworking) {
                readyForNetworking = true;
            }
        }
    }

    private void updateTime() {
        long time = System.nanoTime();
        deltaTime = (time - lastTime) / NANOS_IN_SECOND;
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
        setChanged();
        notifyObservers(MessageFactory.destroyEntity(connectionId, entity));
        getWorld().removeEntity(entity.getId());
        connectedPlayerShips.remove(connectionId);
        System.out.println("Player disconnected: " + connectionId);
    }

    public void updatePlayerControls(Controls updatedControls) {
        recs.Entity entity = getWorld().getEntity(updatedControls.getEntityId());
        Controls oldControls = entity.getComponent(Controls.class);
        oldControls.copyState(updatedControls);
    }
}
