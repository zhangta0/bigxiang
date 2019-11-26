package com.bigxiang.invoker.handle;

import com.bigxiang.constant.MessageTypeCode;
import com.bigxiang.entity.ByteStruct;
import com.bigxiang.factory.SerializerFactory;
import com.bigxiang.invoker.factory.RequestFactory;
import com.bigxiang.invoker.proxy.RequestTask;
import com.bigxiang.provider.entity.ProviderResponse;
import com.bigxiang.serialize.iface.Serializer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Zhon.Thao on 2019/11/16.
 *
 * @author Zhon.Thao
 */
public class ReceiveHandle extends SimpleChannelInboundHandler {

    private RequestFactory requestFactory;

    public ReceiveHandle(RequestFactory requestFactory) {
        super();
        this.requestFactory = requestFactory;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (o instanceof ByteStruct) {
            ByteStruct b = (ByteStruct) o;
            if (b.getMessageType() == MessageTypeCode.RPC_CALL) {
                Serializer serializer = SerializerFactory.get(b.getSerializeType());
                ProviderResponse response = serializer.deserialize(b.getBody(), ProviderResponse.class);
                RequestTask requestTask = requestFactory.remove(response.getSeq());
                if (null != requestTask) {
                    requestTask.setResponse(response.getResult());
                }
            }
        }
    }
}
