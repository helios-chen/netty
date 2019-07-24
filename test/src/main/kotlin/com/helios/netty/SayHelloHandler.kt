package com.helios.netty

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.SimpleChannelInboundHandler
import io.netty.util.CharsetUtil
import org.slf4j.LoggerFactory

class SayHelloHandler : SimpleChannelInboundHandler<ByteBuf>() {

    private val logger = LoggerFactory.getLogger(SayHelloHandler::class.java)

    override fun channelActive(ctx: ChannelHandlerContext) {
        logger.info("connect to server")
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,I'm a client attempting to communicate with you.",CharsetUtil.UTF_8))
    }

    override fun channelRead0(ctx: ChannelHandlerContext, msg: ByteBuf) {
        logger.info("receive msg:${msg.toString(CharsetUtil.UTF_8)}")
//        ctx.writeAndFlush("hello world")
    }
}