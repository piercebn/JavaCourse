package com.piercebn.javacource.nio.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.util.Map;

public class HttpClientHandler extends ChannelInboundHandlerAdapter {
    private Map<String, Object> resultMap;

    public HttpClientHandler(Map resultMap){
        this.resultMap = resultMap;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelActive -> xxxxxxxxx");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("msg -> "+msg);
        if(msg instanceof FullHttpResponse){
            FullHttpResponse response = (FullHttpResponse)msg;
            ByteBuf buf = response.content();
            String result = buf.toString(CharsetUtil.UTF_8);
            System.out.println("response -> "+result);
            resultMap.put("response", response);
        }
    }

}
