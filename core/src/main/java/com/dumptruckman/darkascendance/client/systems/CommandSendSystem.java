package com.dumptruckman.darkascendance.client.systems;

import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.client.components.Player;
import com.dumptruckman.darkascendance.shared.KryoNetwork;
import com.dumptruckman.darkascendance.shared.messages.MessageFactory;
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
