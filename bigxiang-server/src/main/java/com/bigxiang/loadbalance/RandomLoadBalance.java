package com.bigxiang.loadbalance;

import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.loadbalance.iface.LoadBalance;
import com.bigxiang.netty.NettyClient;

import java.util.List;
import java.util.Random;

/**
 * Created by Zhon.Thao on 2019/11/26.
 *
 * @author Zhon.Thao
 */
public class RandomLoadBalance extends LoadBalance {

    @Override
    public NettyClient cal(InvokeConfig invokeConfig, List<NettyClient> nettyClients) {
        if (null != nettyClients && !nettyClients.isEmpty()) {
            return nettyClients.get(new Random().nextInt(nettyClients.size()));
        }
        return null;
    }
}
