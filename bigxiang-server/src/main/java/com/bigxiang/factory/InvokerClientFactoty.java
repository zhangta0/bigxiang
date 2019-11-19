package com.bigxiang.factory;

import com.bigxiang.invoker.client.InvokerClient;
import com.bigxiang.invoker.config.InvokeConfig;
import com.google.common.collect.Maps;

import java.util.Map;

/**
 * Created by Zhon.Thao on 2019/11/18.
 *
 * @author Zhon.Thao
 */
public class InvokerClientFactoty {

    private static final Map<InvokeConfig, InvokerClient> map = Maps.newConcurrentMap();

    public static void add(InvokeConfig invokeConfig, InvokerClient client) {
        map.put(invokeConfig, client);
    }

    public static InvokerClient get(InvokeConfig invokeConfig) {
        return map.get(invokeConfig);
    }
}
