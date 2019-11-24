package com.bigxiang.provider.factory;

import com.bigxiang.invoker.entity.InvokeRequest;
import com.bigxiang.provider.config.ProviderConfig;
import com.bigxiang.util.ClassConvert;
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
    private static final Map<String, Map<ParamWrap, Method>> PROVIDER_METHOD_FACTORY = new ConcurrentHashMap<>();

    public static ProviderConfig get(String url) {
        return PROVIDER_FACTORY.get(url);
    }

    public static void put(String url, ProviderConfig providerConfig) {
        PROVIDER_FACTORY.put(url, providerConfig);
        List<Method> methods = providerConfig.getMethods();
        if (null != methods && methods.size() > 0) {
            for (Method method : methods) {
                String key = getMethodKey(providerConfig.getInterfaceName(), method);
                Map<ParamWrap, Method> paramWrapMethodMap = PROVIDER_METHOD_FACTORY.get(key);
                if (paramWrapMethodMap == null) {
                    paramWrapMethodMap = new HashMap<>(16);
                    PROVIDER_METHOD_FACTORY.put(key, paramWrapMethodMap);
                }
                Class<?>[] paramClz = method.getParameterTypes();
                paramWrapMethodMap.put(new ParamWrap(ClassConvert.convert(paramClz)), method);
            }
        }
    }

    public static Object getBean(String url) {
        ProviderConfig providerConfig = PROVIDER_FACTORY.get(url);
        if (null != providerConfig) {
            providerConfig.getBean();
        }
        return null;
    }

    public static Method getMethod(InvokeRequest invokeRequest) {
        ProviderConfig providerConfig = PROVIDER_FACTORY.get(invokeRequest.getUrl());
        if (null == providerConfig) {
            return null;
        }
        String methodKey = getMethodKey(providerConfig.getInterfaceName(), invokeRequest.getMethodName());
        Map<ParamWrap, Method> paramWrapMethodMap = PROVIDER_METHOD_FACTORY.get(methodKey);
        if (null == paramWrapMethodMap || paramWrapMethodMap.size() == 0) {
            return null;
        }
        return paramWrapMethodMap.get(new ParamWrap(invokeRequest.getArgs()));
    }

    public static Map<String, ProviderConfig> getProviderFactory() {
        return PROVIDER_FACTORY;
    }

    private static String getMethodKey(String interfaceName, Method method) {
        return getMethodKey(interfaceName, method.getName());
    }

    private static String getMethodKey(String interfaceName, String methodName) {
        return interfaceName + "#" + methodName;
    }

    static class ParamWrap {

        private String[] clzStr;
        private int hashcode;

        public ParamWrap(String[] clzStr) {
            this.clzStr = clzStr;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < clzStr.length; i++) {
                sb.append(clzStr[i]).append("#");
            }
            hashcode = sb.toString().hashCode();
        }

        @Override
        public int hashCode() {
            return hashcode;
        }

        @Override
        public boolean equals(Object obj) {
            String[] var2 = (String[]) obj;
            if (clzStr.length == 0 && var2.length == 0) {
                return true;
            }
            if (clzStr.length != var2.length) {
                return false;
            }
            int i = 0;
            for (; i < clzStr.length && clzStr[i++].equals(var2[i++]); ) ;
            return i == clzStr.length;
        }
    }
}
