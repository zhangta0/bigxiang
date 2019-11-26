package com.bigxiang.loadbalance.iface;

import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.netty.NettyClient;

/**
 * Created by zhangtao47 on 2019/11/26.
 *
 * @author zhangtao47
 */
public interface LoadBalance {

    NettyClient load(InvokeConfig invokeConfig);
}
