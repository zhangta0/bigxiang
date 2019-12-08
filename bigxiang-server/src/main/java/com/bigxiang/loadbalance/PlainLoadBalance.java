package com.bigxiang.loadbalance;

import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.invoker.factory.InvokerClientFactory;
import com.bigxiang.loadbalance.iface.LoadBalance;
import com.bigxiang.netty.NettyClient;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhon.Thao on 2019/11/26.
 *
 * @author Zhon.Thao
 */
public class PlainLoadBalance extends LoadBalance {

    @Override
    public NettyClient cal(InvokeConfig invokeConfig, List<NettyClient> nettyClients) {
        Map<InvokeConfig, Iterator<?>> iterableMap = InvokerClientFactory.getIterableMap();
        if (!iterableMap.isEmpty()) {
            Iterator<?> iterator = iterableMap.get(invokeConfig);
            if (null != iterator) {
                if (!iterator.hasNext()) {
                    InvokerClientFactory.iterable(invokeConfig);
                    iterator = iterableMap.get(invokeConfig);
                }
                while (iterator.hasNext()) {
                    return (NettyClient) iterator.next();
                }
            }
        }
        return null;
    }
}
