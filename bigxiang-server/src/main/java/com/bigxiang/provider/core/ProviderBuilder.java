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


}
