package com.dumptruckman.darkascendance.shared.messages;

import com.dumptruckman.darkascendance.shared.Entity;
import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.shared.snapshot.Snapshot;

public class MessageFactory {

    public static Message playerConnected(int connectionId) {
        return new Message()
                .connectionId(connectionId)
                .type(MessageType.PLAYER_CONNECTED)
                .important(true);
    }

    public static Message playerDisconnected(int connectionId) {
        return new Message()
                .connectionId(connectionId)
                .type(MessageType.PLAYER_DISCONNECTED)
                .important(true);
    }

    public static Message createEntity(int connectionId, Entity entity) {
        return new EntityMessage()
                .entity(entity)
                .type(MessageType.CREATE_ENTITY)
                .onlyForConnectionId(connectionId)
                .important(true);
    }

    public static Message destroyEntity(int connectionId, Entity entity) {
        return new EntityMessage()
                .entity(entity)
                .type(MessageType.DESTROY_ENTITY)
                .notForConnectionId(connectionId)
                .important(true);
    }

    public static Message createPlayerShip(int connectionId, Entity entity) {
        return new EntityMessage()
                .entity(entity)
                .type(MessageType.CREATE_PLAYER_SHIP)
                .connectionId(connectionId)
                .important(true);
    }

    public static Message playerInputState(Controls controls) {
        return new ComponentMessage()
                .component(controls)
                .type(MessageType.PLAYER_INPUT_STATE)
                .important(true);
    }

    public static Message createSnapshot(Snapshot snapshot) {
        return new SnapshotMessage()
                .copySnapshot(snapshot)
                .type(MessageType.SNAPSHOT);
    }
}
