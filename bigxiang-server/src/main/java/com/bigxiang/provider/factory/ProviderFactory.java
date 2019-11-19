package com.bigxiang.provider.factory;

import com.bigxiang.provider.config.ProviderConfig;
import com.google.common.collect.Maps;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Zhon.Thao on 2019/4/23.
 *
 * @author Zhon.Thao
 */
public class ProviderFactory {

    private static final Map<String, ProviderConfig> PROVIDER_FACTORY = Maps.newHashMap();
    private static final Map<ProviderConfig, Map<String, Map<String, Method>>> PROVIDER_METHOD_FACTORY = new ConcurrentHashMap<>();

    public static ProviderConfig get(String url) {
        return PROVIDER_FACTORY.get(url);
    }

    public static void put(String url, ProviderConfig providerConfig) {
        PROVIDER_FACTORY.put(url, providerConfig);
        List<Method> methods = providerConfig.getMethods();
        if (null != methods && methods.size() > 0) {
            methods.forEach(method -> {
                PROVIDER_METHOD_FACTORY.get(providerConfig);
                method.getName();
            });
        }
    }
}
