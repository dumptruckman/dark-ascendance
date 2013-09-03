package com.dumptruckman.darkascendance.shared.network;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectSet;
import com.dumptruckman.darkascendance.shared.components.Controls;
import com.dumptruckman.darkascendance.shared.components.Position;
import com.dumptruckman.darkascendance.shared.components.Thrusters;
import com.dumptruckman.darkascendance.shared.components.Velocity;
import com.dumptruckman.darkascendance.shared.messages.Acknowledgement;
import com.dumptruckman.darkascendance.shared.messages.AcknowledgementBatch;
import com.dumptruckman.darkascendance.shared.messages.ComponentMessage;
import com.dumptruckman.darkascendance.shared.messages.EntityMessage;
import com.dumptruckman.darkascendance.shared.messages.Message;
import com.dumptruckman.darkascendance.shared.messages.MessageBase;
import com.dumptruckman.darkascendance.shared.messages.MessageType;
import com.dumptruckman.darkascendance.shared.messages.SnapshotMessage;
import com.dumptruckman.darkascendance.shared.systems.UdpMessageGuarantorSystem;
import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import recs.Component;

import java.util.Observable;
import java.util.Observer;

public abstract class KryoNetwork extends Listener implements Observer {

    private long currentGameTime = 0L;
    private UdpMessageGuarantorSystem udpGuarantor = new UdpMessageGuarantorSystem(this);

    protected void initializeSerializables(Kryo kryo) {
        kryo.register(Component.class);
        kryo.register(Component[].class);
        kryo.register(Object[].class);
        kryo.register(ObjectSet.class);
        kryo.register(Iterable.class);

        kryo.register(Message.class);
        kryo.register(NetworkEntity.class);
        kryo.register(MessageType.class);
        kryo.register(EntityMessage.class);
        kryo.register(ComponentMessage.class);
        kryo.register(SnapshotMessage.class);
        kryo.register(Acknowledgement.class);
        kryo.register(MessageBase.class);
        kryo.register(AcknowledgementBatch.class);

        kryo.register(Controls.class);
        kryo.register(Position.class);
        kryo.register(Thrusters.class);
        kryo.register(Velocity.class);
        kryo.register(Vector2.class);
    }

    protected UdpMessageGuarantorSystem getUdpGuarantor() {
        return udpGuarantor;
    }

    protected abstract void sendMessage(Message message);

    public abstract void resendMessage(int connectionId, Message message);

    public abstract void sendAcknowledgement(int connectionId, AcknowledgementBatch acknowledgementBatch);

    @Override
    public final void update(final Observable o, final Object arg) {
        if (arg instanceof Message) {
            Message message = (Message) arg;
            getUdpGuarantor().guaranteeMessage(message);
            sendMessage(message);
        }
    }

    @Override
    public final void received(final Connection connection, final Object o) {
        if (o instanceof MessageBase) {
            int connectionId = connection.getID();
            MessageBase messageBase = (MessageBase) o;
            int latency = connection.getReturnTripTime();
            getUdpGuarantor().receiveMessage(connectionId, messageBase, latency);
        }
    }

    public abstract void handleMessage(Message message);

    public void setCurrentGameTime(long currentGameTime) {
        this.currentGameTime = currentGameTime;
    }

    public long getCurrentGameTime() {
        return currentGameTime;
    }
}
