package com.bigxiang.provider.handle;

import com.bigxiang.invoker.entity.InvokeRequest;
import com.bigxiang.provider.Exception.ProviderException;
import com.bigxiang.provider.config.ProviderConfig;
import com.bigxiang.provider.entity.ProviderResponse;
import com.bigxiang.provider.factory.ProviderFactory;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.StringUtil;

import java.lang.reflect.Method;

import static com.bigxiang.constant.ChannelAttributeKey.SERIALIZE_TYPE;

/**
 * Created by Zhon.Thao on 2019/4/30.
 *
 * @author Zhon.Thao
 */
@ChannelHandler.Sharable
public class CoreHandle extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof InvokeRequest) {

            InvokeRequest request = (InvokeRequest) msg;
            Method method = volidate(request);

            Object object = ProviderFactory.get(request.getUrl()).getBean();

            Object result = method.invoke(object, request.getValues());
            ProviderResponse providerResponse = new ProviderResponse();
            providerResponse.setSerializeType((byte) ctx.channel().attr(SERIALIZE_TYPE).get());
            providerResponse.setResult(result);

            ctx.writeAndFlush(providerResponse);
        }
    }

    private Method volidate(InvokeRequest request) throws Exception {
        if (StringUtil.isNullOrEmpty(request.getUrl())) {
            throw new ProviderException("invoke url is null");
        }

        ProviderConfig providerConfig = ProviderFactory.get(request.getUrl());
        if (null == providerConfig || providerConfig.getInterfaceName().equals(request.getInterfaceName())) {
            throw new ProviderException("provider is null");
        }

        if (null != providerConfig.getMethods()) {

            Method method = providerConfig.getBean().getClass().getMethod(request.getMethod(), request.getClass());

            if (method == null) {
                throw new ProviderException("provider.method is null");
            }

            return method;
        }

        throw new ProviderException("provider.method is null");
    }

}
