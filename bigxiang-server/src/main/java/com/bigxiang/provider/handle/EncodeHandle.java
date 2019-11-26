package com.bigxiang.provider.handle;

import com.bigxiang.constant.ChannelAttributeKey;
import com.bigxiang.entity.ByteStruct;
import com.bigxiang.factory.SerializerFactory;
import com.bigxiang.provider.entity.ProviderResponse;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * Created by Zhon.Thao on 2019/4/30.
 *
 * @author Zhon.Thao
 */
public class EncodeHandle extends MessageToMessageEncoder {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object o, List list) throws Exception {
        byte serializer = (byte) ctx.channel().attr(ChannelAttributeKey.SERIALIZE_TYPE).get();
        byte messageType = (byte) ctx.channel().attr(ChannelAttributeKey.MESSAGE_TYPE).get();
        long seq = (long) ctx.channel().attr(ChannelAttributeKey.REQUEST_SEQ).get();
        ProviderResponse response = new ProviderResponse(seq, o);
        byte[] b = SerializerFactory.get(serializer).serialize(response);
        list.add(new ByteStruct(serializer, messageType, b.length, b));
    }
}
