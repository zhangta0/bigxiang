package com.bigxiang.listener;

import com.bigxiang.registry.ServiceRegistry;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;

/**
 * Created by Zhon.Thao on 2019/11/29.
 *
 * @author Zhon.Thao
 */
public class ZkConnectionStateListener implements ConnectionStateListener {

    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
        if (connectionState == ConnectionState.RECONNECTED) {
            ServiceRegistry.toRegistry();
        }
    }
}
