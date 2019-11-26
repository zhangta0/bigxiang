package com.bigxiang.loadbalance;

import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.invoker.factory.InvokerClientFactory;
import com.bigxiang.loadbalance.iface.LoadBalance;
import com.bigxiang.netty.NettyClient;

import java.util.List;
import java.util.Random;

/**
 * Created by zhangtao47 on 2019/11/26.
 *
 * @author zhangtao47
 */
public class RandomLoadBalance implements LoadBalance {

    @Override
    public NettyClient load(InvokeConfig invokeConfig) {
        List<NettyClient> nettyClients = InvokerClientFactory.get(invokeConfig);
        if (null != nettyClients && !nettyClients.isEmpty()) {
            return nettyClients.get(new Random().nextInt(nettyClients.size()));
        }
        return null;
    }
}
