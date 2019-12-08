package com.bigxiang.provider.handle;

import com.bigxiang.constant.InvokeType;
import com.bigxiang.exception.ProviderException;
import com.bigxiang.invoker.entity.InvokeRequest;
import com.bigxiang.log.LogFactory;
import com.bigxiang.log.Logger;
import com.bigxiang.provider.config.ProviderConfig;
import com.bigxiang.provider.core.ServiceProcess;
import com.bigxiang.provider.factory.ProviderFactory;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.internal.StringUtil;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * Created by Zhon.Thao on 2019/4/30.
 *
 * @author Zhon.Thao
 */
public class ProcessHandle extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LogFactory.getLogger(ProcessHandle.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof InvokeRequest) {
            InvokeRequest request = (InvokeRequest) msg;
            validate(request);
            Object result = ServiceProcess.process(request);
            if (request.getInvokeType() == InvokeType.SYNC.code) {
                ctx.channel().writeAndFlush(result);
            }
        }
    }

    private void validate(InvokeRequest request) throws Exception {

        ProviderException exception = null;

        if (StringUtil.isNullOrEmpty(request.getUrl())) {
            exception = new ProviderException(request, "invoke url is null");
        }

        if (StringUtils.isEmpty(request.getMethodName())) {
            exception = new ProviderException(request, "invoker param method is null");
        }

        ProviderConfig providerConfig = ProviderFactory.get(request.getUrl());
        if (null == providerConfig || !providerConfig.getInterfaceName().equals(request.getInterfaceName())) {
            exception = new ProviderException(request, "provider is null");
        }

        if (null != providerConfig.getMethods()) {
            Method method = ProviderFactory.getMethod(request);
            if (method == null) {
                exception = new ProviderException(request, "provider method is not exist");
            }
        }

        if (null != exception) {
            LOGGER.error(exception.getMessage(), exception);
            throw exception;
        }
    }
}
