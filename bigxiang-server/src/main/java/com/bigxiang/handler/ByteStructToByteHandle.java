package com.bigxiang.handler;

import com.bigxiang.entity.ByteStruct;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by Zhon.Thao on 2019/11/26.
 *
 * @author Zhon.Thao
 */
public class ByteStructToByteHandle extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf) throws Exception {
        if (byteBuf.isWritable() && o instanceof ByteStruct) {
            ByteStruct b = (ByteStruct) o;
            byteBuf.writeByte(b.getSerializeType());
            byteBuf.writeByte(b.getMessageType());
            byteBuf.writeInt(b.getLength());
            byteBuf.writeBytes(b.getBody());
        }
    }
}
