package com.bigxiang.invoker.handle;

import com.bigxiang.entity.ByteStruct;
import com.bigxiang.factory.SerializerFactory;
import com.bigxiang.invoker.factory.RequestFactory;
import com.bigxiang.invoker.proxy.RequestTask;
import com.bigxiang.serialize.iface.Serializer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Zhon.Thao on 2019/11/16.
 *
 * @author Zhon.Thao
 */
public class ReceiveHandle extends SimpleChannelInboundHandler {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (o instanceof ByteStruct) {
            ByteStruct byteStruct = (ByteStruct) o;
            Serializer serializer = SerializerFactory.get(byteStruct.getSerializeType());
            RequestTask requestTask = RequestFactory.get(byteStruct.getSeq());
            requestTask.setResponse(serializer.deserialize(byteStruct.getBody(), requestTask.getReturnType()));
        }
    }
}
