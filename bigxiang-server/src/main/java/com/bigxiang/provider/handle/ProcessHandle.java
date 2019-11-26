package com.bigxiang.provider.handle;

import com.bigxiang.invoker.entity.InvokeRequest;
import com.bigxiang.provider.Exception.ProviderException;
import com.bigxiang.provider.config.ProviderConfig;
import com.bigxiang.provider.core.ServiceProcess;
import com.bigxiang.provider.factory.ProviderFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.StringUtil;

import java.lang.reflect.Method;

/**
 * Created by Zhon.Thao on 2019/4/30.
 *
 * @author Zhon.Thao
 */
public class ProcessHandle extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof InvokeRequest) {
            InvokeRequest request = (InvokeRequest) msg;
            validate(request);
            Object result = ServiceProcess.process(request);
            ctx.channel().writeAndFlush(result);
        }
    }

    private void validate(InvokeRequest request) throws Exception {
        if (StringUtil.isNullOrEmpty(request.getUrl())) {
            throw new ProviderException("invoke url is null");
        }

        ProviderConfig providerConfig = ProviderFactory.get(request.getUrl());
        if (null == providerConfig || !providerConfig.getInterfaceName().equals(request.getInterfaceName())) {
            throw new ProviderException("provider is null");
        }

        if (null != providerConfig.getMethods()) {

            Method method = ProviderFactory.getMethod(request);
            if (method == null) {
                throw new ProviderException("provider method is not exist");
            }

            return;
        }

        throw new ProviderException("invoker param method is null");
    }

}
