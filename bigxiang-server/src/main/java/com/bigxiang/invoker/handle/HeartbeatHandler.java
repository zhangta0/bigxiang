package com.bigxiang.invoker.handle;

import com.bigxiang.constant.MessageTypeCode;
import com.bigxiang.constant.SerializerEnum;
import com.bigxiang.entity.ByteStruct;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * Created by Zhon.Thao on 2019/12/3.
 *
 * @author Zhon.Thao
 */
public class HeartbeatHandler extends ChannelInboundHandlerAdapter {

    private static ByteStruct byteStruct =
            new ByteStruct(SerializerEnum.HESSIAN.code, MessageTypeCode.HEARTBEAT_CALL, 1, new byte[]{-1});

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state().equals(IdleState.WRITER_IDLE)) {
                ctx.channel().writeAndFlush(byteStruct);
            } else if (event.state().equals(IdleState.READER_IDLE)) {
                ctx.channel().isOpen();
            }
        }
        ctx.fireUserEventTriggered(evt);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        ctx.fireChannelInactive();
    }
}
