package com.bigxiang.registry;

import com.bigxiang.config.ConfigContainer;
import com.bigxiang.entity.HostInfo;
import com.bigxiang.listener.InvokeClientListener;
import com.bigxiang.listener.ZkConnectionStateListener;
import com.bigxiang.log.LogFactory;
import com.bigxiang.log.Logger;
import com.bigxiang.provider.config.ProviderConfig;
import com.bigxiang.util.HostIpUtil;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
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

    private static final Logger LOGGER = LogFactory.getLogger(ZkRegistry.class);
    private static String path = "zookeeper/bigxiang";
    private static String prefix = "/";
    private static RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
    private static final ConfigContainer container = new ConfigContainer();
    private static boolean isStart = false;

    static final CuratorFramework zkClient = CuratorFrameworkFactory.builder()
            .connectString(container.getZkAddress())
            .sessionTimeoutMs(5000)
            .connectionTimeoutMs(3000)
            .retryPolicy(policy)
            .namespace(path)
            .build();

    public ZkRegistry() {
        if (!isStart) {
            zkClient.start();
            isStart = true;
            zkClient.getConnectionStateListenable().addListener(new ZkConnectionStateListener());
        }
    }

    public void registry(ProviderConfig providerConfig) {
        try {
            String path = prefix + providerConfig.getUrl();
            Stat stat = zkClient.checkExists().forPath(path);
            if (null == stat) {
                zkClient.create().withMode(CreateMode.PERSISTENT).forPath(path);
            }
            String host = HostIpUtil.getIpAddress() + ":" + providerConfig.getPort();
            String childPath = path + prefix + host;
            Stat childStat = zkClient.checkExists().forPath(childPath);
            if (null == childStat) {
                zkClient.create().withMode(CreateMode.EPHEMERAL).forPath(childPath);
            }
        } catch (Exception e) {
            LOGGER.error("zk registry error,providerConfig:" + providerConfig.toString(), e);
        }
    }

    public void watch(InvokeClientListener listener) throws Exception {
        PathChildrenCache pathChildrenCache = new PathChildrenCache(zkClient, prefix + listener.getInvokeConfig().getUrl(), true);
        pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        pathChildrenCache.getListenable().addListener(listener);
    }

    public List<HostInfo> getHost(String url) {
        List<HostInfo> hostInfoList = new ArrayList<>();
        try {
            String path = prefix + url;
            List<String> childPathList = zkClient.getChildren().forPath(path);
            if (!childPathList.isEmpty()) {
                for (String childPath : childPathList) {
                    HostInfo hostInfo = new HostInfo(childPath);
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
