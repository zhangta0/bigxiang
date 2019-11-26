package com.bigxiang.handler;

import com.bigxiang.entity.ByteStruct;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by Zhon.Thao on 2019/4/24.
 *
 * @author Zhon.Thao
 */
public class ByteToByteStructHandle extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        if (!byteBuf.isReadable()) {
            return;
        }
        if (byteBuf.readableBytes() < 6) {
            return;
        }

        byte serializeType = byteBuf.readByte();
        byte messageType = byteBuf.readByte();
        int length = byteBuf.readInt();

        if (byteBuf.readableBytes() < length) {
            return;
        }

        byte[] bytes = new byte[length];
        byteBuf.readBytes(bytes);

        ByteStruct byteStruct = new ByteStruct(serializeType, messageType, length, bytes);
        list.add(byteStruct);
    }
}
