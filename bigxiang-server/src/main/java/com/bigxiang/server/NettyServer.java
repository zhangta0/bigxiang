package com.bigxiang.server;

import com.bigxiang.factory.NettyServerFactory;
import com.bigxiang.handler.BytePrepareHandle;
import com.bigxiang.handler.DecodeHandle;
import com.bigxiang.handler.EncodeHandle;
import com.bigxiang.provider.handle.CoreHandle;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Zhon.Thao on 2019/2/13.
 *
 * @author Zhon.Thao
 */
public class NettyServer {

    private static final ChannelHandler BYTEPREPAREHANDLE = new BytePrepareHandle();
    private static final ChannelHandler DECODEHANDLE = new DecodeHandle();
    private static final ChannelHandler COREHANDLE = new CoreHandle();
    private static final ChannelHandler ENCODEHANDLE = new EncodeHandle();

    private ServerBootstrap bootstrap;
    private int port;
    private boolean started;

    public NettyServer(int port) {
        this.port = port;
        bootstrap = new ServerBootstrap()
                .group(new NioEventLoopGroup(1), new NioEventLoopGroup())
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(BYTEPREPAREHANDLE)
                                .addLast(DECODEHANDLE)
                                .addLast(COREHANDLE)
                                .addLast(ENCODEHANDLE);
                    }
                });
    }

    public void start() {
        if (!started) {
            bootstrap.bind(port);
            started = true;
        }
    }

    public void close() {
        started = false;
        bootstrap = null;
        NettyServerFactory.remove(port);
    }
}
