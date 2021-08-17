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



### 作业3

完善网关的例子，实现过滤器，解析header中的信息



### 作业4

完善网关的例子，实现路由，采用随机方式



### 作业5

多线程示例



### 作业6

完善网关的例子，调整线程池参数，优化性能











