package com.bigxiang.registry;

import com.bigxiang.config.ConfigContainer;
import com.bigxiang.entity.HostInfo;
import com.bigxiang.provider.config.ProviderConfig;
import com.bigxiang.util.HostIpUtil;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhon.Thao on 2019/4/30.
 *
 * @author Zhon.Thao
 */
public class ZkRegistry {

    private static String path = "zookeeper/bigxiang";
    private static String preix = "/";
    static RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
    static boolean isStart = false;

    static final CuratorFramework zkClient = CuratorFrameworkFactory.builder()
            .connectString(ConfigContainer.getZkAddress())
            .sessionTimeoutMs(5000)
            .connectionTimeoutMs(3000)
            .retryPolicy(policy)
            .namespace(path)
            .build();

    public ZkRegistry() {
        if (!isStart) {
            zkClient.start();
            isStart = true;
        }
    }

    public void registry(ProviderConfig providerConfig) {
        try {

            Stat stat = zkClient.checkExists().forPath(providerConfig.getUrl());
            String path = preix + providerConfig.getUrl();
            if (null == stat) {
                zkClient.create().withMode(CreateMode.PERSISTENT).forPath(path);
            }

            final NodeCache cache = new NodeCache(zkClient, path, false);
            cache.start(true);
            cache.getListenable().addListener(new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    ChildData currentData = cache.getCurrentData();
                }
            });
            String host = HostIpUtil.getIpAddress() + ":" + providerConfig.getPort();
            zkClient.create().withMode(CreateMode.EPHEMERAL).forPath(path + preix + host);
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    public List<HostInfo> getHost(String url) {
        List<HostInfo> hostInfoList = new ArrayList<>();
        try {
            List<String> childPathList = zkClient.getChildren().forPath(preix + url);
            if (!childPathList.isEmpty()) {
                for (String childPath : childPathList) {
                    String[] s = childPath.split(":");
                    HostInfo hostInfo = new HostInfo(s[0], Integer.parseInt(s[1]));
                    hostInfoList.add(hostInfo);
                }
            }
            return hostInfoList;
        } catch (Exception e) {
            System.err.print("zk registry error");
        }
        return null;
    }
}
