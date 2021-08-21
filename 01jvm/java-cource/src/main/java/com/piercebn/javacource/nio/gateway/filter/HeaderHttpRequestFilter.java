package com.piercebn.javacource.nio.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class HeaderHttpRequestFilter implements HttpRequestFilter {
    @Override
    public boolean filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        boolean isPass = true;
        String uri = fullRequest.uri();
        if (!uri.startsWith("/hello")) {
            isPass = false;
        }
        fullRequest.headers().set("proxy-header", "HeaderHttpRequestFilter");
        return isPass;
    }
}
