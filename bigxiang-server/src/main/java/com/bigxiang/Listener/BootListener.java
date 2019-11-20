package com.bigxiang.Listener;

import com.bigxiang.factory.NettyServerFactory;
import com.bigxiang.invoker.annotation.Invoker;
import com.bigxiang.invoker.client.InvokerClient;
import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.invoker.proxy.ProxyFactory;
import com.bigxiang.provider.annotation.Provider;
import com.bigxiang.provider.config.ProviderConfig;
import com.bigxiang.provider.core.PortAutoGet;
import com.bigxiang.provider.factory.ProviderFactory;
import com.bigxiang.server.NettyServer;
import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

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
        Class clz = bean.getClass();
        Provider provider = (Provider) clz.getAnnotation(Provider.class);
        if (null == provider) {
            return bean;
        }

        Class[] interfaces = clz.getInterfaces();
        if (interfaces.length == 0) {
            throw new RuntimeException("provider no interface");
        }

        String url = provider.url();
        url = StringUtils.isEmpty(url) ? interfaces[0].getName() : url;

        ProviderConfig providerConfig = new ProviderConfig.Builder()
                .url(url)
                .Bean(bean)
                .InterfaceName(interfaces[0].getName())
                .Methods(Lists.newArrayList(interfaces[0].getMethods()))
                .port(PortAutoGet.port)
                .build();

        ProviderFactory.put(url, providerConfig);
        NettyServerFactory.put(PortAutoGet.port, new NettyServer(PortAutoGet.port));

        List<Method> methods = Lists.newArrayList(bean.getClass().getMethods());
        for (Method method : methods) {
            Invoker invoker = method.getAnnotation(Invoker.class);
            if (null != invoker) {

            }
        }

        return bean;
    }
}
