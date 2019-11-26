package com.bigxiang.provider.handle;

import com.bigxiang.constant.ChannelAttributeKey;
import com.bigxiang.entity.ByteStruct;
import com.bigxiang.factory.SerializerFactory;
import com.bigxiang.invoker.entity.InvokeRequest;
import com.bigxiang.serialize.iface.Serializer;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.AttributeKey;

import java.util.List;

/**
 * Created by Zhon.Thao on 2019/4/24.
 *
 * @author Zhon.Thao
 */
public class DecodeHandle extends MessageToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, Object o, List list) throws Exception {
        if (o instanceof ByteStruct) {
            ByteStruct byteStruct = (ByteStruct) o;
            Serializer serializer = SerializerFactory.get(byteStruct.getSerializeType());
            InvokeRequest invokeRequest = serializer.deserialize(byteStruct.getBody(), InvokeRequest.class);
            ctx.channel().attr(ChannelAttributeKey.SERIALIZE_TYPE).set(byteStruct.getSerializeType());
            ctx.channel().attr(ChannelAttributeKey.MESSAGE_TYPE).set(byteStruct.getMessageType());
            ctx.channel().attr(ChannelAttributeKey.REQUEST_SEQ).set(invokeRequest.getSeq());
            ctx.fireChannelRead(invokeRequest);
        }
    }
}
