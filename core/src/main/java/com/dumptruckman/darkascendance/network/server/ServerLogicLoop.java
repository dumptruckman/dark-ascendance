package com.dumptruckman.darkascendance.network.server;

import com.artemis.Entity;
import com.artemis.World;
import com.badlogic.gdx.utils.TimeUtils;
import com.dumptruckman.darkascendance.core.GameLogic;
import com.dumptruckman.darkascendance.network.messages.Messages;

class ServerLogicLoop extends GameLogic implements Runnable {

    private static final float NANOS_IN_SECOND = 1000000000.0F;

    private volatile boolean readyForNetworking = false;
    private float deltaTime = 0L;
    private float lastTime = 0L;

    public ServerLogicLoop() {
        super(new World());
        addLogicSystemsAndInitializeWorld();
    }

    @Override
    public void run() {
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
        Entity entity = getEntityFactory().createBasicShip();
        setChanged();
        notifyObservers(Messages.createPlayerShip(connectionId, entity));
        entity.addToWorld();
    }
}
