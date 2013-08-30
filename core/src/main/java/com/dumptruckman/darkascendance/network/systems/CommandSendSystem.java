package com.dumptruckman.darkascendance.network.systems;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.dumptruckman.darkascendance.core.IntervalEntityProcessingSystem;
import com.dumptruckman.darkascendance.core.components.Controls;
import com.dumptruckman.darkascendance.core.components.Player;
import com.dumptruckman.darkascendance.network.KryoNetwork;
import com.dumptruckman.darkascendance.network.messages.MessageFactory;

public class CommandSendSystem extends IntervalEntityProcessingSystem {

    private KryoNetwork kryoNetwork;

    @Mapper
    ComponentMapper<Controls> controlsMap;

    public CommandSendSystem(KryoNetwork kryoNetwork, final float interval) {
        super(Aspect.getAspectForAll(Player.class, Controls.class), interval);
        this.kryoNetwork = kryoNetwork;
    }

    @Override
    protected void process(final Entity e) {
        kryoNetwork.sendMessage(0, MessageFactory.playerInputState(controlsMap.get(e)));
    }
}
