package com.rannn.netty.basic;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hztaoran on 2016/7/22.
 */
public class EchoClientHandler extends ChannelHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(EchoServerHandler1.class);

    private final ByteBuf firstMessge;

    public EchoClientHandler() {
        byte[] req = "ECHO".getBytes();
        firstMessge = Unpooled.buffer(req.length);
        firstMessge.writeBytes(req);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(firstMessge);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        logger.info("TimeClientHandler.channelRead: ");

        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "UTF-8");
        System.out.println("Current time is : " + body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.warn("Unexpected exception from downstream :" + cause.getMessage());
        ctx.close();
    }
}
