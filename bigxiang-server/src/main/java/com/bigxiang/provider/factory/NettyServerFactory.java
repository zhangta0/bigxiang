package com.bigxiang.provider.factory;

import com.bigxiang.server.NettyServer;
import com.google.common.collect.Maps;

import java.util.Iterator;
import java.util.Map;

/**
 * Created by Zhon.Thao on 2019/4/30.
 *
 * @author Zhon.Thao
 */
public class NettyServerFactory {

    private static final Map<Integer, NettyServer> map = Maps.newConcurrentMap();

    public static void put(Integer port, NettyServer nettyServer) {
        map.put(port, nettyServer);
    }

    public static void get(Integer port, NettyServer nettyServer) {
        map.get(port);
    }

    public static void remove(Integer port) {
        map.remove(port);
    }

    public static void start() {
        if (null != map && !map.isEmpty()) {
            Iterator<NettyServer> iterator = map.values().iterator();
            while (iterator.hasNext()) {
                iterator.next().start();
            }
        }
    }
}
