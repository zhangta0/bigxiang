package com.bigxiang.invoker.factory;

import com.bigxiang.entity.HostInfo;
import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.listener.InvokeClientListener;
import com.bigxiang.netty.NettyClient;
import com.bigxiang.registry.ZkRegistry;

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
    private static final Map<InvokeConfig, Iterator<?>> iterableMap = new ConcurrentHashMap<>();

    public static void init(InvokeConfig invokeConfig) throws Exception {
        List<HostInfo> hostInfoList = registry.getHost(invokeConfig.getUrl());
        if (null != hostInfoList && !hostInfoList.isEmpty()) {
            for (HostInfo hostInfo : hostInfoList) {
                new NettyClient(invokeConfig, hostInfo).start();
            }
        }
        registry.watch(new InvokeClientListener(invokeConfig));
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
        if (null != nettyClients && !nettyClients.isEmpty()) {
            iterableMap.put(invokeConfig, nettyClients.iterator());
        }
    }

    public static void remove(InvokeConfig invokeConfig, HostInfo hostInfo) {
        List<NettyClient> nettyClients = map.get(invokeConfig);
        if (null != nettyClients && !nettyClients.isEmpty()) {
            Iterator<NettyClient> iterator = nettyClients.iterator();
            while (iterator.hasNext()) {
                NettyClient client = iterator.next();
                if (hostInfo.equals(client.getHostInfo())) {
                    client.close();
                    iterator.remove();
                }
            }
            iterableMap.put(invokeConfig, iterator);
        }
    }

    public static List<NettyClient> get(InvokeConfig invokeConfig) {
        List<NettyClient> nettyClients = map.get(invokeConfig);
        if (null != nettyClients && !nettyClients.isEmpty()) {
            return nettyClients;
        }
        try {
            init(invokeConfig);
        } catch (Exception e) {
        }
        List<NettyClient> clients = map.get(invokeConfig);
        if (null == clients || clients.isEmpty()) {
            throw new RuntimeException(String.format("not find provider,invokeConfig:%s", invokeConfig.toString()));
        }
        return clients;
    }

    public static Map<InvokeConfig, Iterator<?>> getIterableMap() {
        return iterableMap;
    }

    public static void close() {
        for (Map.Entry<?, List<NettyClient>> entry : map.entrySet()) {
            Iterator<NettyClient> iterator = entry.getValue().iterator();
            while (iterator.hasNext()) {
                iterator.next().close();
            }
        }
    }
}
