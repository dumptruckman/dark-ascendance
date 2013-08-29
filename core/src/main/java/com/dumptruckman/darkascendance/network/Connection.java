package com.dumptruckman.darkascendance.network;

import io.netty.channel.ChannelFuture;

public class Connection extends Thread {

    protected ChannelFuture channelFuture;

    public boolean isConnected() {
        return channelFuture != null && channelFuture.channel().isActive();
    }

    public void sendMessage(Object message) {
        if  (isConnected()) {
            channelFuture.channel().writeAndFlush(message);
        }
    }
}
