package com.bigxiang.provider.core;

import com.bigxiang.exception.ProviderException;
import com.bigxiang.invoker.entity.InvokeRequest;
import com.bigxiang.log.LogFactory;
import com.bigxiang.log.Logger;
import com.bigxiang.provider.factory.ProviderFactory;

import java.lang.reflect.Method;

/**
 * Created by Zhon.Thao on 2019/11/21.
 *
 * @author Zhon.Thao
 */
public class ServiceProcess {

    private static final Logger LOGGER = LogFactory.getLogger(ServiceProcess.class);

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
            String msg = "provider process fail,request" + request.toString();
            LOGGER.error(msg, e);
            return new ProviderException(msg, e);
        }
    }
}
