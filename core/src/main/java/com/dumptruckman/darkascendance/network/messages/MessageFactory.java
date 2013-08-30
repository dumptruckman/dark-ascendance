package com.dumptruckman.darkascendance.network.messages;

import com.artemis.Entity;
import com.dumptruckman.darkascendance.core.components.Controls;

public class MessageFactory {

    public static Message createPlayerShip(int connectionId, Entity entity) {
        return new EntityMessage()
                .entity(entity)
                .type(MessageType.CREATE_PLAYER_SHIP)
                .connectionId(connectionId);
    }

    public static Message playerInputState(Controls controls) {
        return new ComponentMessage()
                .component(controls)
                .type(MessageType.PLAYER_INPUT_STATE);
    }
}
