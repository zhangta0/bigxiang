package com.bigxiang.server;

import com.bigxiang.handler.BytePrepareHandle;
import com.bigxiang.handler.DecodeHandle;
import com.bigxiang.handler.EncodeHandle;
import com.bigxiang.provider.handle.CoreHandle;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by Zhon.Thao on 2019/4/30.
 *
 * @author Zhon.Thao
 */
public class NettyClient {

    private static final ChannelHandler BYTEPREPAREHANDLE = new BytePrepareHandle();
    private static final ChannelHandler DECODEHANDLE = new DecodeHandle();
    private static final ChannelHandler COREHANDLE = new CoreHandle();
    private static final ChannelHandler ENCODEHANDLE = new EncodeHandle();

    private Bootstrap bootstrap;
    private int port;
    private boolean connected;

    public NettyClient(int port) {
        this.port = port;
        bootstrap = new Bootstrap().group(new NioEventLoopGroup(1))
                .channel(NioServerSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
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

//    public void connect() {
//        if (!connected) {
//            bootstrap.connect();
//            started = true;
//        }
//    }
//
//    public void close() {
//        started = false;
//        bootstrap = null;
//        NettyServerFactory.remove(port);
//    }
}
