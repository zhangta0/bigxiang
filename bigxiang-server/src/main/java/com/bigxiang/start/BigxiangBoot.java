package com.bigxiang.start;

import com.bigxiang.invoker.annotation.Invoker;
import com.bigxiang.invoker.config.InvokeConfig;
import com.bigxiang.invoker.factory.InvokerClientFactory;
import com.bigxiang.invoker.proxy.ProxyFactory;
import com.bigxiang.listener.BigxiangListener;
import com.bigxiang.netty.NettyServer;
import com.bigxiang.provider.annotation.Provider;
import com.bigxiang.provider.config.ProviderConfig;
import com.bigxiang.provider.factory.NettyServerFactory;
import com.bigxiang.provider.factory.ProviderFactory;
import com.google.common.collect.Lists;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;

/**
 * Created by Zhon.Thao on 2019/11/18.
 *
 * @author Zhon.Thao
 */
public class BigxiangBoot extends BigxiangListener implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            Invoker invoker = field.getAnnotation(Invoker.class);
            if (null != invoker) {
                if (!field.getType().isInterface()) {
                    throw new UnsupportedOperationException();
                }
                InvokeConfig invokeConfig = new InvokeConfig();
                invokeConfig.setInterfaceClz(field.getType());
                if (StringUtils.isEmpty(invoker.url())) {
                    invokeConfig.setUrl(invokeConfig.getInterfaceClz().getName());
                }
                invokeConfig.setSerializer(invoker.serializer().code);
                invokeConfig.setTimeout(invoker.timeout());
                invokeConfig.setTypeEnum(invoker.invokeType());
                field.setAccessible(true);
                try {
                    InvokerClientFactory.init(invokeConfig);
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
            throw new BeanCreationException("provider is not a interface,[beanName]" + beanName);
        }

        String url = provider.url();
        url = StringUtils.isEmpty(url) ? interfaces[0].getName() : url;

        ProviderConfig providerConfig = new ProviderConfig.Builder()
                .url(url)
                .Bean(bean)
                .InterfaceName(interfaces[0].getName())
                .Methods(Lists.newArrayList(interfaces[0].getMethods()))
                .port(this.getServerPort())
                .build();

        ProviderFactory.put(url, providerConfig);
        NettyServerFactory.put(providerConfig.getPort(), new NettyServer(providerConfig.getPort()));
        return bean;
    }
}
