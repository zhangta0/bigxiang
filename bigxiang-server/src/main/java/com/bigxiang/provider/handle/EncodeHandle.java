package com.bigxiang.provider.handle;

import com.bigxiang.constant.ChannelAttributeKey;
import com.bigxiang.entity.ByteStruct;
import com.bigxiang.factory.SerializerFactory;
import com.bigxiang.provider.entity.ProviderResponse;
import com.bigxiang.serialize.iface.Serializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Zhon.Thao on 2019/4/30.
 *
 * @author Zhon.Thao
 */
@ChannelHandler.Sharable
public class EncodeHandle extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object o, ByteBuf byteBuf) throws Exception {
        if (byteBuf.isWritable()) {
            byte serializer = (byte) ctx.channel().attr(ChannelAttributeKey.SERIALIZE_TYPE).get();
            long seq = (long) ctx.channel().attr(ChannelAttributeKey.REQUEST_SEQ).get();
            byte[] b = SerializerFactory.get(serializer).serialize(o);
            byteBuf.writeByte(serializer);
            byteBuf.writeLong(seq);
            byteBuf.writeInt(b.length);
            byteBuf.writeBytes(b);
        }
    }
}
