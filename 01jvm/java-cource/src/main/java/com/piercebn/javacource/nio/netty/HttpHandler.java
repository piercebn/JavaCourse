package com.piercebn.javacource.nio.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.ReferenceCountUtil;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            //logger.info("channelRead流量接口请求开始，时间为{}", startTime);
            FullHttpRequest fullRequest = (FullHttpRequest) msg;
            String uri = fullRequest.uri();
            //logger.info("接收到的请求url为{}", uri);
            if (uri.contains("/doGetNetty")) {
                handlerDoGetNetty(fullRequest, ctx, "localhost",8801,"/");
            } else if (uri.contains("/doGet")) {
                handlerDoGet(fullRequest, ctx, "http://localhost:8801/");
            } else if (uri.contains("/test")) {
                handlerTest(fullRequest, ctx, "hello,kimmking");
            } else {
                handlerTest(fullRequest, ctx, "hello,others");
            }
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void handlerTest(FullHttpRequest fullRequest, ChannelHandlerContext ctx, String body) {
        FullHttpResponse response = null;
        try {
            String value = body; // 对接上次作业的httpclient或者okhttp请求另一个url的响应数据

//            httpGet ...  http://localhost:8801
//            返回的响应，"hello,nio";
//            value = reponse....

            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());

        } catch (Exception e) {
            System.out.println("处理出错:"+e.getMessage());
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
                ctx.flush();
            }
        }
    }

    private void handlerDoGet(FullHttpRequest fullRequest, ChannelHandlerContext ctx, String uri) {
        FullHttpResponse response = null;
        try {
            //使用httpclient请求另一个url的响应数据
            response = doGet(uri);
        } catch (Exception e) {
            System.out.println("处理出错:"+e.getMessage());
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
                ctx.flush();
            }
        }
    }

    private void handlerDoGetNetty(FullHttpRequest fullRequest, ChannelHandlerContext ctx, String host, int port, String path) {
        FullHttpResponse response = null;
        try {
            //使用httpclient请求另一个url的响应数据
            response = NettyHttpClient.start(host, port, path);
        } catch (Exception e) {
            System.out.println("处理出错:"+e.getMessage());
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
        } finally {
            if (fullRequest != null) {
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
                ctx.flush();
            }
        }
    }

    private FullHttpResponse doGet(String uri) throws IOException {
        FullHttpResponse resultResponse = null;
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(uri);
        CloseableHttpResponse response = httpclient.execute(httpGet);
        try {
            System.out.println("Status: "+response.getStatusLine());
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                String result = EntityUtils.toString(entity);
                System.out.println("Result: " + result);
                resultResponse = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(result.getBytes("UTF-8")));
                for (Header header:response.getAllHeaders()) {
                    resultResponse.headers().set(header.getName(), header.getValue());
                }
            }
        } finally {
            response.close();
        }
        return resultResponse;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }

}
