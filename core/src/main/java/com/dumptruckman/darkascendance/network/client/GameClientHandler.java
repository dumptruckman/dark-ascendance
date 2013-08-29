package com.dumptruckman.darkascendance.network.client;

import com.dumptruckman.darkascendance.network.protos.DarkAscendanceProtos.Test;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class GameClientHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg; // (1)
        try {

            ctx.close();
        } finally {
            m.release();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

    //@Override
    //public void channelActive(ChannelHandlerContext ctx) {
        //Test test = Test.newBuilder().setTestMessage("Hello, world!").build();
    //    ChannelFuture f = ctx.writeAndFlush(test);
    //    f.addListener(ChannelFutureListener.CLOSE);
    //}
}
