package com.bigxiang.loadbalance.iface;

import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.invoker.factory.InvokerClientFactory;
import com.bigxiang.netty.NettyClient;
import com.bigxiang.util.ThreadPoolExecutorBuilder;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by Zhon.Thao on 2019/11/26.
 *
 * @author Zhon.Thao
 */
public abstract class LoadBalance {

    private static final ThreadPoolExecutor threadPool = new ThreadPoolExecutorBuilder().build();

    public NettyClient load(InvokeConfig invokeConfig) {
        List<NettyClient> nettyClients = InvokerClientFactory.get(invokeConfig);
        if (null == nettyClients || nettyClients.isEmpty()) {
            return null;
        }

        long start = System.currentTimeMillis();
        while (true && (System.currentTimeMillis() - start) < 3000) {
            final NettyClient nettyClient = cal(invokeConfig, nettyClients);
            if (null != nettyClient) {
                if (nettyClient.isClosed()) {
                    nettyClients.remove(nettyClient);
                } else if (!nettyClient.isOpen()) {
                    threadPool.submit(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                nettyClient.connect();
                            } catch (Exception e) {
                            }
                        }
                    });
                } else {
                    return nettyClient;
                }
            }
        }
        return null;
    }

    public abstract NettyClient cal(InvokeConfig invokeConfig, List<NettyClient> nettyClients);
}
