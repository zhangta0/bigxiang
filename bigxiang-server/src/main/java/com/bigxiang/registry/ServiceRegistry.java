package com.bigxiang.registry;

import com.bigxiang.provider.config.ProviderConfig;
import com.bigxiang.provider.factory.ProviderFactory;
import com.bigxiang.util.ThreadPoolExecutorBuilder;

import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by zhangtao47 on 2019/11/21.
 *
 * @author zhangtao47
 */
public class ServiceRegistry {

    public static final ZkRegistry zkRegistry = new ZkRegistry();
    public static final ThreadPoolExecutor executor = new ThreadPoolExecutorBuilder().core(5).max(10).build();

    public static void toRegistry() {
        Map<String, ProviderConfig> providerConfigMap = ProviderFactory.getProviderFactory();
        if (null != providerConfigMap && providerConfigMap.size() > 0) {
            for (final ProviderConfig providerConfig : providerConfigMap.values()) {
                executor.submit(new Runnable() {
                    @Override
                    public void run() {
                        zkRegistry.registry(providerConfig);
                    }
                });
            }
        }
    }
}
