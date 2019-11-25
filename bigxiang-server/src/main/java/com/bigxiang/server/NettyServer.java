package com.bigxiang.server;

import com.bigxiang.handler.UnPackageHandle;
import com.bigxiang.provider.factory.NettyServerFactory;
import com.bigxiang.provider.handle.DecodeHandle;
import com.bigxiang.provider.handle.EncodeHandle;
import com.bigxiang.provider.handle.ProcessHandle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Zhon.Thao on 2019/2/13.
 *
 * @author Zhon.Thao
 */
public class NettyServer {

    private ServerBootstrap bootstrap;
    private int port;
    private boolean started;
    private Channel channel;

    public NettyServer(int port) {
        this.port = port;
        bootstrap = new ServerBootstrap()
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .group(new NioEventLoopGroup(1), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new UnPackageHandle());
                        pipeline.addLast(new DecodeHandle());
                        pipeline.addLast(new ProcessHandle());
                        pipeline.addLast(new EncodeHandle());
                    }
                });
    }

    public void start() {
        if (!started) {
            try {
                ChannelFuture future = bootstrap.bind(port).sync();
                channel = future.channel();
                started = true;
            } catch (Exception e) {
                System.err.print("server start fail");
            }
        }
    }

    public void close() {
        started = false;
        bootstrap = null;
        NettyServerFactory.remove(port);
        channel.close();
    }
}
