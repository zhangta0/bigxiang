package com.bigxiang.invoker.client;

import com.bigxiang.entity.HostInfo;
import com.bigxiang.factory.InvokerClientFactoty;
import com.bigxiang.factory.SerializerFactory;
import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.invoker.entity.InvokeRequest;
import com.bigxiang.registry.ZkRegistry;
import com.bigxiang.serialize.iface.Serializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by Zhon.Thao on 2019/11/14.
 *
 * @author Zhon.Thao
 */
public class InvokerClient {

    private static Channel channel;
    private static boolean closed;
    private static final ZkRegistry registry = new ZkRegistry();

    private InvokeConfig invokeConfig;
    private HostInfo hostInfo;

    public InvokerClient(InvokeConfig invokeConfig) {
        this.invokeConfig = invokeConfig;
        hostInfo = registry.getHost(invokeConfig.getUrl());
    }

    public void init() throws Exception {
        if (null == hostInfo) {
            throw new Exception("not find provider");
        }
        if (!closed) {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(new NioEventLoopGroup()).channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            // 处理来自服务端的响应信息
                            socketChannel.pipeline().addLast(new ReceiveHandle());
                        }
                    });

            ChannelFuture cf = bootstrap.connect(hostInfo.getIp(), hostInfo.getPort()).sync();
            channel = cf.channel();
            InvokerClientFactoty.add(invokeConfig, this);
        }
    }

    public Object call(InvokeRequest invokeRequest) throws Exception {
        if (!channel.isOpen()) {
            init();
        }
        RequestTask requestTask = new RequestTask();
        RequestFactory.put(invokeRequest.getSeq(), requestTask);
        Serializer serializer = SerializerFactory.get(invokeRequest.getSerializer());
        channel.writeAndFlush(serializer.serialize(invokeRequest));
        return requestTask.getResponse(invokeRequest.getTimeout());
    }

    public void close() {
        channel.close();
        closed = true;
    }
}
