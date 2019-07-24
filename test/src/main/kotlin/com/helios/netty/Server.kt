package com.helios.netty

import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import org.slf4j.LoggerFactory

class Server {

    val logger = LoggerFactory.getLogger(Server::class.java)

    fun start(port: Int) {
        val bootstrap = ServerBootstrap()
        val acceptorLoopGroup = NioEventLoopGroup()
        val workerLoopGroup = NioEventLoopGroup()
        try {
            bootstrap.group(acceptorLoopGroup, workerLoopGroup)
                .channel(NioServerSocketChannel::class.java)
                .option(ChannelOption.SO_BACKLOG, 10)
//                .handler(LoggingHandler(LogLevel.INFO))
                .childHandler(object : ChannelInitializer<Channel>() {
                    override fun initChannel(ch: Channel) {
                        ch.pipeline().addLast("echo_handler", EchoHandler())
                    }
                })

            val future = bootstrap.bind(port).sync()
            logger.info("server is starting listening ${future.channel().localAddress()}")
            future.channel().closeFuture().sync()
        }
        catch (e:Exception)  {
            logger.error("unknown exception",e)
        }
        finally {
            acceptorLoopGroup.shutdownGracefully().sync()
            workerLoopGroup.shutdownGracefully().sync()
        }

    }


}

fun main() {
    Server().start(65535)
}