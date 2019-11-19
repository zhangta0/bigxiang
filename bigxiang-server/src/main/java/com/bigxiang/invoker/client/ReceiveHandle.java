package com.bigxiang.invoker.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Zhon.Thao on 2019/11/16.
 *
 * @author Zhon.Thao
 */
public class ReceiveHandle extends SimpleChannelInboundHandler {

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {

        Response response = (Response) o;

        RequestTask requestTask = RequestFactory.get(response.getSeq());
        requestTask.setResponse(response);
    }
}
