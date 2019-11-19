package com.bigxiang.invoker.proxy;

import com.bigxiang.factory.InvokerClientFactoty;
import com.bigxiang.invoker.client.InvokerClient;
import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.invoker.entity.InvokeRequest;
import com.bigxiang.registry.ZkRegistry;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Created by Zhon.Thao on 2019/4/30.
 *
 * @author Zhon.Thao
 */
public class InvokeInvocation implements InvocationHandler {

    private static final ZkRegistry registry = new ZkRegistry();
    private InvokeConfig invokeConfig;

    public InvokeInvocation(InvokeConfig invokeConfig) {
        this.invokeConfig = invokeConfig;
        registry.getHost(invokeConfig.getUrl());
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        InvokerClient client = InvokerClientFactoty.get(invokeConfig);
        InvokeRequest request = new InvokeRequest();
        request.setArgs(method.getParameterTypes());
        request.setInterfaceName(invokeConfig.getInterfaceClz().getName());
        request.setMethod(method.getName());
        request.setSerializer(invokeConfig.getSerializer());
        request.setUrl(invokeConfig.getUrl());
        request.setValues(Arrays.asList(args));
        request.setTimeout(invokeConfig.getTimeout());
        return client.call(request);
    }
}
