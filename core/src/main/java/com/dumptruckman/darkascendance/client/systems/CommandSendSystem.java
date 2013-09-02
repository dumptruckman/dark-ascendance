package com.dumptruckman.darkascendance.client.systems;

import com.dumptruckman.darkascendance.client.ClientLogicLoop;
import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.client.components.Player;
import com.dumptruckman.darkascendance.shared.network.KryoNetwork;
import com.dumptruckman.darkascendance.shared.messages.MessageFactory;
import recs.IntervalEntitySystem;

public class CommandSendSystem extends IntervalEntitySystem {

    private ClientLogicLoop client;

    private static Controls playerControls = null;

    public CommandSendSystem(ClientLogicLoop client, final float interval) {
        super(interval, Player.class, Controls.class);
        this.client = client;
    }

    public static void setPlayerControlsChanged(Controls controls) {
        System.out.println("Player controls changed: " + controls.getEntityId());
        playerControls = controls;
    }

    @Override
    protected void processEntity(int entityId, float deltaInSec) {
        if (playerControls != null) {
            client.controlsPrepared(playerControls);
            playerControls = null;
        }
    }
}
