package com.helios.netty

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.util.CharsetUtil
import org.slf4j.LoggerFactory

class EchoHandler :ChannelInboundHandlerAdapter() {

    val logger = LoggerFactory.getLogger(EchoHandler::class.java)


    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val byteBuf = msg as ByteBuf
        logger.info("receiver msg:${byteBuf.toString(CharsetUtil.UTF_8)}")
        ctx.writeAndFlush(Unpooled.copiedBuffer("Hello,I'm the server!!!",CharsetUtil.UTF_8))
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
//        logger.info("reading msg finished")
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER)
        super.channelReadComplete(ctx)
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}