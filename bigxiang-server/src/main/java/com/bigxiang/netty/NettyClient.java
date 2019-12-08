package com.bigxiang.netty;

import com.bigxiang.constant.InvokeType;
import com.bigxiang.entity.HostInfo;
import com.bigxiang.exception.InvokerException;
import com.bigxiang.handler.ByteStructToByteHandle;
import com.bigxiang.handler.ByteToByteStructHandle;
import com.bigxiang.heartbeat.HeartbeatEvent;
import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.invoker.entity.InvokeRequest;
import com.bigxiang.invoker.factory.InvokerClientFactory;
import com.bigxiang.invoker.factory.RequestFactory;
import com.bigxiang.invoker.handle.EncodeHandle;
import com.bigxiang.invoker.handle.ReceiveHandle;
import com.bigxiang.invoker.proxy.RequestTask;
import com.bigxiang.log.LogFactory;
import com.bigxiang.log.Logger;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by Zhon.Thao on 2019/11/14.
 *
 * @author Zhon.Thao
 */
public class NettyClient {

    private static final Logger LOGGER = LogFactory.getLogger(NettyClient.class);
    private Channel channel;
    private boolean closed;
    private final AtomicLong atomic = new AtomicLong();
    private final RequestFactory requestFactory = new RequestFactory();

    private InvokeConfig invokeConfig;
    private HostInfo hostInfo;
    private Bootstrap bootstrap;
    private HeartbeatEvent heartbeatTask;

    public NettyClient(InvokeConfig invokeConfig, HostInfo hostInfo) {
        this.hostInfo = hostInfo;
        this.invokeConfig = invokeConfig;
        heartbeatTask = new HeartbeatEvent(this);
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
                                .addLast(new EncodeHandle())
                                .addLast(new IdleStateHandler(2, 1, 0));
                    }
                });
    }

    public NettyClient start() throws Exception {
        connect();
        if (null != channel) {
            InvokerClientFactory.add(invokeConfig, this);
            heartbeatTask.start();
        }
        return this;
    }

    public void connect() throws Exception {
        if (null == hostInfo) {
            throw new InvokerException("not find provider,InvokeConfig" + invokeConfig.toString());
        }
        if (!closed) {
            ChannelFuture cf = bootstrap.connect(hostInfo.getIp(), hostInfo.getPort());
            if (cf.awaitUninterruptibly(3, TimeUnit.SECONDS)) {
                if (cf.isSuccess()) {
                    if (cf.channel().isActive()) {
                        channel = cf.channel();
                    }
                }
                if (null == channel) {
                    LOGGER.warn(String.format("bigxiang connect fail,invokeConfig:%s,hostInfo:%s",
                            invokeConfig.toString(), hostInfo.toString()));
                }
            }
        }
    }

    public Object call(InvokeRequest invokeRequest) throws Exception {
        Object o = null;
        long seq = 0;
        try {
            if (invokeRequest.getInvokeType() == InvokeType.SYNC.code) {
                RequestTask requestTask = new RequestTask(invokeRequest.getReturnType());
                seq = atomic.getAndAdd(1);
                invokeRequest.setSeq(seq);
                requestFactory.put(seq, requestTask);
                write(invokeRequest);
                o = requestTask.getResponse(invokeRequest.getTimeout());
                if (o != null && o instanceof Exception) {
                    Exception e = (Exception) o;
                    LOGGER.error(e.getMessage() + String.format("[invokeRequest]:%s", invokeRequest.toString()), e);
                    throw e;
                }
            } else {
                write(invokeRequest);
            }
            return o;
        } finally {
            if (invokeRequest.getInvokeType() == InvokeType.SYNC.code) {
                requestFactory.remove(seq);
            }
        }
    }

    public void write(Object object) {
        channel.writeAndFlush(object);
    }

    public void close() {
        channel.close();
        closed = true;
    }

    public boolean isClosed() {
        return closed;
    }

    public boolean isOpen() {
        return channel.isOpen() && channel.isActive();
    }

    public HostInfo getHostInfo() {
        return hostInfo;
    }


}
