package com.dumptruckman.darkascendance.network.messages;

import com.artemis.Entity;

public class Messages {

    public static Message createPlayerShip(int connectionId, Entity entity) {
        return new EntityMessage()
                .entity(entity)
                .type(MessageType.CREATE_PLAYER_SHIP)
                .connectionId(connectionId);
    }
}
