package com.bigxiang.listener;

import com.bigxiang.entity.HostInfo;
import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.invoker.factory.InvokerClientFactory;
import com.bigxiang.netty.NettyClient;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

/**
 * Created by Zhon.Thao on 2019/11/28.
 *
 * @author Zhon.Thao
 */
public class InvokeClientListener implements PathChildrenCacheListener {

    private InvokeConfig invokeConfig;

    public InvokeClientListener(InvokeConfig invokeConfig) {
        this.invokeConfig = invokeConfig;
    }

    @Override
    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent event) throws Exception {
        ChildData childData = event.getData();
        String[] paths = childData.getPath().split("/");
        String path = paths[paths.length - 1];
        HostInfo hostInfo = new HostInfo(path);
        switch (event.getType()) {
            case CHILD_ADDED:
                new NettyClient(invokeConfig, hostInfo).start();
                break;
            case CHILD_REMOVED:
                InvokerClientFactory.remove(invokeConfig, hostInfo);
                break;
            case CHILD_UPDATED:
                break;
            default:
                break;
        }
    }

    public InvokeConfig getInvokeConfig() {
        return invokeConfig;
    }
}
