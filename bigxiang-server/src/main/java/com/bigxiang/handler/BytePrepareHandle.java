package com.bigxiang.handler;

import com.bigxiang.constant.ChannelAttributeKey;
import com.bigxiang.entity.ByteStruct;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.AttributeKey;

import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by Zhon.Thao on 2019/4/24.
 *
 * @author Zhon.Thao
 */
@ChannelHandler.Sharable
public class BytePrepareHandle extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (!byteBuf.isReadable()) {
            return;
        }
        if (byteBuf.readableBytes() < 5) {
            return;
        }

        byte serializeType = byteBuf.readByte();
        int length = byteBuf.readInt();

        if (byteBuf.readableBytes() < length) {
            return;
        }

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        ByteStruct byteStruct = new ByteStruct(serializeType, length, bytes);
        list.add(byteStruct);

        ctx.channel().attr(ChannelAttributeKey.SERIALIZE_TYPE).set(serializeType);
    }
}
