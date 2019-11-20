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
    private static final Map<String, Map<ParamWrap, Method>> PROVIDER_METHOD_FACTORY = new ConcurrentHashMap<>();

    public static ProviderConfig get(String url) {
        return PROVIDER_FACTORY.get(url);
    }

    public static void put(String url, ProviderConfig providerConfig) {
        PROVIDER_FACTORY.put(url, providerConfig);
        List<Method> methods = providerConfig.getMethods();
        if (null != methods && methods.size() > 0) {
            methods.forEach(method -> {
                String key = getMethodKey(providerConfig.getInterfaceName(), method);
                Map<ParamWrap, Method> paramWrapMethodMap = PROVIDER_METHOD_FACTORY.get(key);
                if (paramWrapMethodMap == null) {
                    paramWrapMethodMap = new HashMap<>(16);
                    PROVIDER_METHOD_FACTORY.put(key, paramWrapMethodMap);
                }
                Class<?>[] paramClz = method.getParameterTypes();
                paramWrapMethodMap.put(new ParamWrap(paramClz), method);
            });
        }
    }

    public static getFiled(String param) {

    }

    private static String getMethodKey(String interfaceName, Method method) {
        return interfaceName + "#" + method.getName();
    }

    static class ParamWrap {

        private String[] clzStr;
        private int hashcode;

        public ParamWrap(Class<?>[] paramClz) {
            clzStr = new String[paramClz.length];
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < paramClz.length; i++) {
                clzStr[i] = paramClz[i].getName();
                sb.append(clzStr[i]).append("#");
            }
            hashcode = sb.toString().hashCode();
        }

        public ParamWrap(String[] paramClz) {
            clzStr = new String[paramClz.length];
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < paramClz.length; i++) {
                //clzStr[i] = paramClz[i].getName();
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
