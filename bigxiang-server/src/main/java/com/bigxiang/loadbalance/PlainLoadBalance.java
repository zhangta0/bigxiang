package com.bigxiang.loadbalance;

import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.invoker.factory.InvokerClientFactory;
import com.bigxiang.loadbalance.iface.LoadBalance;
import com.bigxiang.netty.NettyClient;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by zhangtao47 on 2019/11/26.
 *
 * @author zhangtao47
 */
public class PlainLoadBalance implements LoadBalance {

    @Override
    public NettyClient load(InvokeConfig invokeConfig) {
        Map<InvokeConfig, Iterator<?>> iterableMap = InvokerClientFactory.iterableMap;
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
