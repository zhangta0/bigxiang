package com.bigxiang.Listener;

import com.bigxiang.invoker.annotation.Invoker;
import com.bigxiang.invoker.client.InvokerClient;
import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.invoker.proxy.ProxyFactory;
import com.bigxiang.provider.annotation.Provider;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * Created by Zhon.Thao on 2019/11/18.
 *
 * @author Zhon.Thao
 */
@Component
public class BootListener implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Invoker.class)) {
                if (!field.getClass().isInterface()) {
                    throw new UnsupportedOperationException();
                }
                Invoker invoker = field.getAnnotation(Invoker.class);
                InvokeConfig invokeConfig = new InvokeConfig();
                invokeConfig.setInterfaceClz(field.getClass());
                if (StringUtils.isEmpty(invoker.url())) {
                    invokeConfig.setUrl(invokeConfig.getInterfaceClz().getName());
                }
                invokeConfig.setSerializer(invoker.serializer().code);
                invokeConfig.setTimeout(invoker.timeout());
                field.setAccessible(true);
                try {
                    new InvokerClient(invokeConfig).init();
                    field.set(bean, ProxyFactory.newProxyBean(invokeConfig));
                } catch (Exception e) {
                    throw new BeanInitializationException("init fail", e);
                }
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean.getClass().isAnnotationPresent(Provider.class)) {


        }
        return bean;
    }
}
