package com.bigxiang.invoker.proxy;

import com.bigxiang.config.ConfigContainer;
import com.bigxiang.factory.LoadbalanceFactory;
import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.loadbalance.iface.LoadBalance;
import com.bigxiang.netty.NettyClient;

/**
 * Created by Zhon.Thao on 2019/11/28.
 *
 * @author Zhon.Thao
 */
public class InvokerClientLoadBalance {

    private static final ConfigContainer container = new ConfigContainer();
    public static final LoadBalance loadBalance = LoadbalanceFactory.get(container.getLoadBalance());

    public static NettyClient loadBalance(InvokeConfig invokeConfig) {
        return loadBalance.load(invokeConfig);
    }
}
