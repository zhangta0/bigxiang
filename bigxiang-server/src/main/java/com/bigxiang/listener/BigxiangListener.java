package com.bigxiang.listener;

import com.bigxiang.config.ConfigContainer;
import com.bigxiang.factory.LoadbalanceFactory;
import com.bigxiang.factory.SerializerFactory;
import com.bigxiang.invoker.factory.InvokerClientFactory;
import com.bigxiang.netty.NettyClient;
import com.bigxiang.provider.factory.NettyServerFactory;
import com.bigxiang.registry.ServiceRegistry;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStoppedEvent;

/**
 * Created by Zhon.Thao on 2019/11/27.
 *
 * @author Zhon.Thao
 */
public class BigxiangListener extends ConfigContainer implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if (applicationEvent instanceof ContextRefreshedEvent) {
            SerializerFactory.init();
            LoadbalanceFactory.init();
            NettyServerFactory.start();
            ServiceRegistry.toRegistry();
        }
        if (applicationEvent instanceof ContextClosedEvent) {
            InvokerClientFactory.close();
            NettyServerFactory.close();
        }
    }
}
