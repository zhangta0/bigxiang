package com.bigxiang.provider.handle;

import com.bigxiang.entity.ByteStruct;
import com.bigxiang.factory.SerializerFactory;
import com.bigxiang.invoker.entity.InvokeRequest;
import com.bigxiang.serialize.iface.Serializer;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.AttributeKey;

import java.util.List;

/**
 * Created by Zhon.Thao on 2019/4/24.
 *
 * @author Zhon.Thao
 */
@ChannelHandler.Sharable
public class DecodeHandle extends MessageToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, Object o, List list) throws Exception {
        if (o instanceof ByteStruct) {
            ByteStruct byteStruct = (ByteStruct) o;
            Serializer serializer = SerializerFactory.get(byteStruct.getSerializeType());
            InvokeRequest invokeRequest = serializer.deserialize(byteStruct.getBody(), InvokeRequest.class);
            ctx.fireChannelRead(invokeRequest);
        }
    }
}
