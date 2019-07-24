package com.helios.netty

import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import org.slf4j.LoggerFactory

class Client {

    private val logger = LoggerFactory.getLogger(Client::class.java)

    fun start(host: String, port: Int) {
        val group = NioEventLoopGroup()
        try {
            val bootstrap = Bootstrap()
            bootstrap.group(group)
                .channel(NioSocketChannel::class.java)
                .remoteAddress(host,port)
                .handler(object : ChannelInitializer<SocketChannel>() {
                    override fun initChannel(ch: SocketChannel) {
                        ch.pipeline().addLast(SayHelloHandler())
                    }
                })
//            val future = bootstrap.connect(host, port).sync()
            val future = bootstrap.connect().sync()
            future.channel().closeFuture().sync()
        } catch (e: Exception) {
            logger.error("unknown client err occurs",e)
        } finally {
            group.shutdownGracefully().sync()
        }
    }
}

fun main() {
    val host = "127.0.0.1"
    val port = 65535
    Client().start(host,port)
}