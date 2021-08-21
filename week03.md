# 第三周作业

## 作业题目

1.（必做）整合你上次作业的 httpclient/okhttp；
2.（选做）使用 netty 实现后端 http 访问（代替上一步骤）
3.（必做）实现过滤器。
4.（选做）实现路由。
5.（选做）跑一跑课上的各个例子，加深对多线程的理解
6.（选做）完善网关的例子，试着调整其中的线程池参数

## 作业完成说明

### 作业1

在netty服务端代码里整合httpclient（ 采用通过访问http://localhost:8808/doGet来请求http://localhost:8801/ ）

> 先运行HttpServer01（http://localhost:8801/ ）
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/HttpServer01.java
>
> 再运行NettyHttpServer（http://localhost:8808/doGet）
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/netty/NettyHttpServer.java
>
> 整合代码在HttpHandler里
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/netty/HttpHandler.java

```java
@Override
public void channelRead(ChannelHandlerContext ctx, Object msg) {
  try {
    //logger.info("channelRead流量接口请求开始，时间为{}", startTime);
    FullHttpRequest fullRequest = (FullHttpRequest) msg;
    String uri = fullRequest.uri();
    //logger.info("接收到的请求url为{}", uri);
    if (uri.contains("/doGet")) {
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
```

### 作业2

在netty服务端代码里，使用netty 实现后端 http 访问

参考资料

> netty官网：
>
> https://netty.io/wiki/user-guide-for-4.x.html
>
> 其他参考链接：
>
> https://blog.csdn.net/wangshuang1631/article/details/73251180/
>
> https://www.cnblogs.com/silyvin/p/12113175.html

在netty服务端代码里，整合NettyHttpClient代码（ 采用通过访问http://localhost:8808/doGetNetty来请求http://localhost:8801/ ）

> 先运行HttpServer01（http://localhost:8801/ ）
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/HttpServer01.java
>
> 再运行NettyHttpServer（http://localhost:8808/doGetNetty）
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/netty/NettyHttpServer.java
>
> 整合代码在HttpHandler里，同时新增NettyHttpClient代码
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/netty/HttpHandler.java
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/netty/NettyHttpClient.java
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/netty/HttpClientHandler.java

```java
/* HttpHandler.java */
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
```

```java
/* NettyHttpClient.java */
public class NettyHttpClient {
    public static FullHttpResponse start(String host, int port, String path) throws InterruptedException, URISyntaxException {
        Map<String, Object> map = new ConcurrentHashMap<>();
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        try {
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<Channel>() {

                        @Override
                        protected void initChannel(Channel channel)
                                throws Exception {
                            channel.pipeline().addLast(new HttpRequestEncoder());
                            channel.pipeline().addLast(new HttpResponseDecoder());
                            channel.pipeline().addLast(new HttpClientCodec());
                            channel.pipeline().addLast(new HttpObjectAggregator(65536));
                            channel.pipeline().addLast(new HttpContentDecompressor());
                            channel.pipeline().addLast(new HttpClientHandler(map));
                        }
                    });
            ChannelFuture future = bootstrap.connect(host, port).sync();

            URI uri = new URI(path);
            FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, uri.toASCIIString());
            request.headers().add(HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE);
            request.headers().add(HttpHeaderNames.CONTENT_LENGTH,request.content().readableBytes());
            // 发送http请求
            future.channel().write(request);
            future.channel().flush();
            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
        return (FullHttpResponse)map.get("response");
    }
}
```

```java
/* HttpClientHandler.java */
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
```

### 作业3

完善网关的例子，实现过滤器，向header中追加信息

> 实现request filter和response filter，修改header信息
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/gateway/filter/HeaderHttpRequestFilter.java
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/gateway/filter/HeaderHttpResponseFilter.java

```java
public class HeaderHttpRequestFilter implements HttpRequestFilter {
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set("mao", "soul");
    }
}
public class HeaderHttpResponseFilter implements HttpResponseFilter {
    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("kk", "java-1-nio");
    }
}
```

> 通过HttpOutboundHandler实现httpclient请求封装，在请求前调用HttpRequestFilter，向请求头追加信息，在请求返回结果后返回前调用HttpResponseFilter，向响应头追加信息
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/gateway/outbound/httpclient4/HttpOutboundHandler.java

```java
public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) {
  String backendUrl = router.route(this.backendUrls);
  final String url = backendUrl + fullRequest.uri();
  // 请求过滤调用
  filter.filter(fullRequest, ctx);
  proxyService.submit(()->fetchGet(fullRequest, ctx, url));
}
private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final HttpResponse endpointResponse) throws Exception {
  FullHttpResponse response = null;
  try {
    byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
    response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
    response.headers().set("Content-Type", "application/json");
    response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));
    // 响应过滤调用
    filter.filter(response);
  } catch (Exception e) {
    e.printStackTrace();
    response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
    exceptionCaught(ctx, e);
  } finally {
    if (fullRequest != null) {
      if (!HttpUtil.isKeepAlive(fullRequest)) {
        ctx.write(response).addListener(ChannelFutureListener.CLOSE);
      } else {
        ctx.write(response);
      }
    }
    ctx.flush();
  }
}
```

### 作业4

完善网关的例子，实现路由，采用随机方式

> 实现随机路由
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/gateway/router/RandomHttpEndpointRouter.java

```java
public class RandomHttpEndpointRouter implements HttpEndpointRouter {
    @Override
    public String route(List<String> urls) {
        int size = urls.size();
        Random random = new Random(System.currentTimeMillis());
        return urls.get(random.nextInt(size));
    }
}
```

> 通过HttpOutboundHandler实现httpclient请求封装，在请求前调用HttpEndpointRouter，进行随机路由选择，确定访问地址
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/gateway/outbound/httpclient4/HttpOutboundHandler.java

```java
public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) {
  // 请求路由选择
  String backendUrl = router.route(this.backendUrls);
  final String url = backendUrl + fullRequest.uri();
  filter.filter(fullRequest, ctx);
  proxyService.submit(()->fetchGet(fullRequest, ctx, url));
}
```

### 作业5

多线程示例



### 作业6

完善网关的例子，调整线程池参数，优化性能











