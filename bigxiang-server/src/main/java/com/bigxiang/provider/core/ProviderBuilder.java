package com.bigxiang.provider.core;

import com.bigxiang.factory.NettyServerFactory;
import com.bigxiang.invoker.annotation.Invoker;
import com.bigxiang.provider.annotation.Provider;
import com.bigxiang.provider.config.ProviderConfig;
import com.bigxiang.provider.factory.ProviderFactory;
import com.bigxiang.server.NettyServer;
import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Zhon.Thao on 2019/4/23.
 *
 * @author Zhon.Thao
 */
@Configuration
@Import(ProviderBuilder.class)
public class ProviderBuilder implements BeanPostProcessor {

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
