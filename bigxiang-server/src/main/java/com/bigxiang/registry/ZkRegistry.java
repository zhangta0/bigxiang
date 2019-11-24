package com.bigxiang.registry;

import com.bigxiang.entity.HostInfo;
import com.bigxiang.provider.config.ProviderConfig;
import com.bigxiang.util.HostIpUtil;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

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
            .connectString("localhost:2181")
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
            String host = HostIpUtil.getIpAddress() + ":" + providerConfig.getPort();
            zkClient.create().withMode(CreateMode.EPHEMERAL).forPath(preix + providerConfig.getUrl(), host.getBytes());
        } catch (Exception e) {
            System.exit(-1);
        }
    }

    public HostInfo getHost(String url) {
        try {
            String host = new String(zkClient.getData().forPath(preix + url), "utf-8");
            String[] s = host.split(":");
            return new HostInfo(s[0], Integer.parseInt(s[1]));
        } catch (Exception e) {
            System.err.print("zk registry error");
        }
        return null;
    }
}
