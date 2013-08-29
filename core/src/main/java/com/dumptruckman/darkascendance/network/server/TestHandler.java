package com.dumptruckman.darkascendance.network.server;

import com.dumptruckman.darkascendance.network.protos.DarkAscendanceProtos.Test;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.ReferenceCountUtil;

public class TestHandler extends SimpleChannelInboundHandler<Test> {

    @Override
    protected void channelRead0(final ChannelHandlerContext ctx, final Test msg) throws Exception {
        try {
            System.out.println("received: " + msg.getTestMessage());
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
