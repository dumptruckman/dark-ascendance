package com.dumptruckman.darkascendance.network;

import com.dumptruckman.darkascendance.network.protos.DarkAscendanceProtos.Test;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

public class PipelineConfigurator {

    private final ChannelPipeline pipeline;

    public PipelineConfigurator(ChannelPipeline pipeline) {
        this.pipeline = pipeline;
    }

    public void configureDecodersAndEncoders() {
        configureDecoders();
        configureEncoders();
    }

    private void configureDecoders() {
        pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(1048576, 0, 4, 0, 4));
        pipeline.addLast("protobufDecoder-Test", new ProtobufDecoder(Test.getDefaultInstance()));
    }

    private void configureEncoders() {
        pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
        pipeline.addLast("protobufEncoder", new ProtobufEncoder());
    }
}
