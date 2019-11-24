package com.bigxiang.provider.core;

import com.bigxiang.invoker.entity.InvokeRequest;
import com.bigxiang.provider.factory.ProviderFactory;

import java.lang.reflect.Method;

/**
 * Created by zhangtao47 on 2019/11/21.
 *
 * @author zhangtao47
 */
public class ServiceProcess {

    public static Object process(InvokeRequest request) {
        Object object = ProviderFactory.getBean(request.getUrl());
        Method method = ProviderFactory.getMethod(request);
        try {
            int size = request.getValues().size();
            Object[] objects = new Object[size];
            for (int i = 0; i < size; i++) {
                objects[i] = request.getValues().get(i);
            }
            return method.invoke(object, objects);
        } catch (Exception e) {
            throw new IllegalArgumentException("provider process fail");
        }
    }
}
