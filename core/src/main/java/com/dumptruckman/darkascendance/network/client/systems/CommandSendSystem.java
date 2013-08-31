package com.dumptruckman.darkascendance.network.client.systems;

import com.dumptruckman.darkascendance.core.components.Controls;
import com.dumptruckman.darkascendance.network.client.components.Player;
import com.dumptruckman.darkascendance.network.KryoNetwork;
import com.dumptruckman.darkascendance.network.messages.MessageFactory;
import recs.ComponentMapper;
import recs.IntervalEntitySystem;

public class CommandSendSystem extends IntervalEntitySystem {

    private KryoNetwork kryoNetwork;

    private static Controls playerControls = null;

    public CommandSendSystem(KryoNetwork kryoNetwork, final float interval) {
        super(interval, Player.class, Controls.class);
        this.kryoNetwork = kryoNetwork;
    }

    public static void setPlayerControlsChanged(Controls controls) {
        System.out.println("Player controls changed: " + controls.getEntityId());
        playerControls = controls;
    }

    @Override
    protected void processEntity(int entityId, float deltaInSec) {
        if (playerControls != null) {
            kryoNetwork.sendMessage(MessageFactory.playerInputState(playerControls));
            playerControls = null;
        }
    }
}
