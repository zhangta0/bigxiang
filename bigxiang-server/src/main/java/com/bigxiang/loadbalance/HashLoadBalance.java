package com.bigxiang.loadbalance;

import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.loadbalance.iface.LoadBalance;
import com.bigxiang.netty.NettyClient;

import java.util.List;

/**
 * Created by Zhon.Thao on 2019/11/26.
 *
 * @author Zhon.Thao
 */
public class HashLoadBalance extends LoadBalance {

    @Override
    public NettyClient cal(InvokeConfig invokeConfig, List<NettyClient> nettyClients) {
        if (null != nettyClients && !nettyClients.isEmpty()) {
            return nettyClients.get(invokeConfig.hashCode() % nettyClients.size());
        }
        return null;
    }
}
