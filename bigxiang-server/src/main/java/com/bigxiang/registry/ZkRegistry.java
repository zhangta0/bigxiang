package com.bigxiang.registry;

import com.bigxiang.entity.HostInfo;
import com.bigxiang.provider.config.ProviderConfig;
import com.bigxiang.util.HostIpUtil;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * Created by Zhon.Thao on 2019/4/30.
 *
 * @author Zhon.Thao
 */
public class ZkRegistry {

    private static String path = "zookeeper/bigxiang";
    static RetryPolicy policy = new ExponentialBackoffRetry(1000, 3);
    static boolean isStart = false;

    static final CuratorFramework zkClient = CuratorFrameworkFactory.builder()
            .connectString("localhost:32770")
            .sessionTimeoutMs(5000)
            .connectionTimeoutMs(3000)
            .retryPolicy(policy)
            .namespace(path)
            .build();

    public void ZkRegistry() {
        if (!isStart) {
            zkClient.start();
            isStart = true;
        }
    }

    public void registry(ProviderConfig providerConfig) {
        String host = HostIpUtil.getIpAddress() + ":" + providerConfig.getPort();
        try {
            zkClient.create().forPath(providerConfig.getUrl(), host.getBytes());
        } catch (Exception e) {
            System.err.print("zk registry error");
        }
    }

    public HostInfo getHost(String url) {
        try {
            String host = new String(zkClient.getData().forPath(url), "utf-8");
            String[] s = host.split(":");
            return new HostInfo(s[0], Integer.parseInt(s[1]));
        } catch (Exception e) {
            System.err.print("zk registry error");
        }
        return null;
    }
}
