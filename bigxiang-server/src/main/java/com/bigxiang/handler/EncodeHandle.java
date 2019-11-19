package com.bigxiang.handler;

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
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (o instanceof ProviderResponse) {
            ProviderResponse response = (ProviderResponse) o;
            Serializer serializer = SerializerFactory.get(response.getSerializeType());
            byte[] bytes = serializer.serialize(response.getResult());
            if (byteBuf.isWritable()) {
                byteBuf.writeByte(response.getSerializeType());
                byteBuf.writeInt(bytes.length);
                byteBuf.writeBytes(bytes);
            }
        }
    }
}
