package com.bigxiang.invoker.factory;

import com.bigxiang.config.ConfigContainer;
import com.bigxiang.entity.HostInfo;
import com.bigxiang.factory.LoadbalanceFactory;
import com.bigxiang.loadbalance.iface.LoadBalance;
import com.bigxiang.netty.NettyClient;
import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.registry.ZkRegistry;
import com.google.common.collect.Maps;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zhon.Thao on 2019/11/18.
 *
 * @author Zhon.Thao
 */
public class InvokerClientFactory {

    private static final ZkRegistry registry = new ZkRegistry();
    private static final Map<InvokeConfig, List<NettyClient>> map = new ConcurrentHashMap<>();
    public static final Map<InvokeConfig, Iterator<?>> iterableMap = new ConcurrentHashMap<>();
    public static final LoadBalance loadBalance = LoadbalanceFactory.get(ConfigContainer.getLoadbalance());

    public static void init(InvokeConfig invokeConfig) throws Exception {
        List<HostInfo> hostInfoList = registry.getHost(invokeConfig.getUrl());
        if (!hostInfoList.isEmpty()) {
            for (HostInfo hostInfo : hostInfoList) {
                add(invokeConfig, new NettyClient(invokeConfig, hostInfo).init());
            }
        }
    }

    public static void add(InvokeConfig invokeConfig, NettyClient client) {
        List<NettyClient> nettyClients = map.get(invokeConfig);
        if (null == nettyClients) {
            nettyClients = new ArrayList<>();
            map.put(invokeConfig, nettyClients);
        }
        nettyClients.add(client);
        iterable(invokeConfig);
    }

    public static void iterable(InvokeConfig invokeConfig) {
        List<NettyClient> nettyClients = map.get(invokeConfig);
        if (!nettyClients.isEmpty()) {
            iterableMap.put(invokeConfig, nettyClients.iterator());
        }
    }

    public static List<NettyClient> get(InvokeConfig invokeConfig) {
        return map.get(invokeConfig);
    }

    public static NettyClient loadBalance(InvokeConfig invokeConfig) {
        return loadBalance.load(invokeConfig);
    }
}
