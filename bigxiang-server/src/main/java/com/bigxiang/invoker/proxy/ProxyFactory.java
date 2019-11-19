package com.bigxiang.invoker.proxy;

import com.bigxiang.invoker.config.InvokeConfig;

import java.lang.reflect.Proxy;

/**
 * Created by Zhon.Thao on 2019/4/30.
 *
 * @author Zhon.Thao
 */
public class ProxyFactory {

    public static <T> T newProxyBean(InvokeConfig invokeConfig) throws Exception {
        Class interfaceClz = invokeConfig.getInterfaceClz();
        return (T) Proxy.newProxyInstance(interfaceClz.getClassLoader(),
                new Class[]{interfaceClz},
                new InvokeInvocation(invokeConfig));
    }
}
