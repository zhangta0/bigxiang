package com.bigxiang.netty;

import com.bigxiang.entity.HostInfo;
import com.bigxiang.handler.ByteStructToByteHandle;
import com.bigxiang.handler.ByteToByteStructHandle;
import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.invoker.entity.InvokeRequest;
import com.bigxiang.invoker.factory.InvokerClientFactory;
import com.bigxiang.invoker.factory.RequestFactory;
import com.bigxiang.invoker.handle.EncodeHandle;
import com.bigxiang.invoker.handle.ReceiveHandle;
import com.bigxiang.invoker.proxy.RequestTask;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Zhon.Thao on 2019/11/14.
 *
 * @author Zhon.Thao
 */
public class NettyClient {

    private Channel channel;
    private boolean closed;
    private final AtomicLong atomic = new AtomicLong();
    private final RequestFactory requestFactory = new RequestFactory();

    private InvokeConfig invokeConfig;
    private HostInfo hostInfo;
    private Bootstrap bootstrap;

    public NettyClient(InvokeConfig invokeConfig, HostInfo hostInfo) {
        this.hostInfo = hostInfo;
        this.invokeConfig = invokeConfig;
        bootstrap = new Bootstrap();
        bootstrap.group(new NioEventLoopGroup()).channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline()
                                .addLast(new ByteToByteStructHandle())
                                .addLast(new ReceiveHandle(requestFactory))
                                .addLast(new ByteStructToByteHandle())
                                .addLast(new EncodeHandle());
                    }
                });
    }

    public NettyClient init() throws Exception {
        if (null == hostInfo) {
            throw new Exception("not find provider");
        }
        if (!closed) {
            ChannelFuture cf = bootstrap.connect(hostInfo.getIp(), hostInfo.getPort());
            if (cf.awaitUninterruptibly(3, TimeUnit.SECONDS)) {
                if (cf.isSuccess()) {
                    if (cf.channel().isActive()) {
                        channel = cf.channel();
                    }
                }
            }

            InvokerClientFactory.add(invokeConfig, this);
        }
        return this;
    }

    public Object call(InvokeRequest invokeRequest) throws Exception {
        if (!channel.isOpen()) {
            init();
        }
        RequestTask requestTask = new RequestTask(invokeRequest.getReturnType());
        long seq = atomic.getAndAdd(1);
        invokeRequest.setSeq(seq);
        requestFactory.put(seq, requestTask);
        channel.writeAndFlush(invokeRequest);
        Object o = requestTask.getResponse(invokeRequest.getTimeout());
        requestFactory.remove(seq);
        return o;
    }

    public void close() {
        channel.close();
        closed = true;
    }
}
