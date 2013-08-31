package com.dumptruckman.darkascendance.network.client.systems;

import com.dumptruckman.darkascendance.core.components.Controls;
import com.dumptruckman.darkascendance.core.components.Player;
import com.dumptruckman.darkascendance.network.KryoNetwork;
import com.dumptruckman.darkascendance.network.messages.MessageFactory;
import recs.ComponentMapper;
import recs.IntervalEntitySystem;

public class CommandSendSystem extends IntervalEntitySystem {

    private KryoNetwork kryoNetwork;

    ComponentMapper<Controls> controlsMap;

    public CommandSendSystem(KryoNetwork kryoNetwork, final float interval) {
        super(interval, Player.class, Controls.class);
        this.kryoNetwork = kryoNetwork;
    }

    @Override
    protected void processEntity(int entityId, float deltaInSec) {
        kryoNetwork.sendMessage(0, MessageFactory.playerInputState(controlsMap.get(entityId)));
    }
}
