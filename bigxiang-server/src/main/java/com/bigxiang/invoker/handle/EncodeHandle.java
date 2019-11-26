package com.bigxiang.invoker.handle;

import com.bigxiang.constant.MessageTypeCode;
import com.bigxiang.entity.ByteStruct;
import com.bigxiang.factory.SerializerFactory;
import com.bigxiang.invoker.entity.InvokeRequest;
import com.bigxiang.serialize.iface.Serializer;
import io.netty.channel.ChannelHandlerContext;
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
        if (o instanceof InvokeRequest) {
            InvokeRequest request = (InvokeRequest) o;
            Serializer serializer = SerializerFactory.get(request.getSerializer());
            byte[] bytes = serializer.serialize(request);
            ByteStruct byteStruct = new ByteStruct(request.getSerializer(), MessageTypeCode.RPC_CALL, bytes.length, bytes);
            list.add(byteStruct);
        }
    }
}
