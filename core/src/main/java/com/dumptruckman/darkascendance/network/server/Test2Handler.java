package com.dumptruckman.darkascendance.network.server;

import com.dumptruckman.darkascendance.network.protos.DarkAscendanceProtos.Test;
import com.dumptruckman.darkascendance.network.protos.DarkAscendanceProtos.Test2;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

public class Test2Handler extends SimpleChannelInboundHandler<Test2> {

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final Test2 msg) throws Exception {
        try {
            System.out.println("1: " + msg.getTestMessage());
            System.out.println("2: " + msg.getTestMessage2());
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
