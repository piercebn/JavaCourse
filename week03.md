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

> 先运行HttpServer01（http://localhost:8801/ ）
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/HttpServer01.java
>
> 先运行HttpServer02（http://localhost:8802/ ）
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/HttpServer02.java
>
> 再运行NettyServerGateway（http://localhost:8888/ or http://localhost:8888/hello）
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/gateway/NettyServerGateway.java

> 实现request filter和response filter，修改header信息，并根据url进行请求过滤
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/gateway/filter/HeaderHttpRequestFilter.java
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/gateway/filter/HeaderHttpResponseFilter.java

```java
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
public class HeaderHttpResponseFilter implements HttpResponseFilter {
    @Override
    public void filter(FullHttpResponse response) {
        response.headers().set("proxy-header", "HeaderHttpResponseFilter");
    }
}
```

> 通过HttpOutboundHandler实现httpclient请求封装，在请求前调用HttpRequestFilter，向请求头追加信息，并根据请求url是否以"/hello"开头确定过滤是否放行，在请求返回结果后返回前调用HttpResponseFilter，向响应头追加信息
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/nio/gateway/outbound/httpclient4/HttpOutboundHandler.java

```java
public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) throws Exception {
  String backendUrl = router.route(this.backendUrls);
  final String url = backendUrl + fullRequest.uri();
  // 请求过滤调用
  if (filter.filter(fullRequest, ctx)){
    // 通过发起请求
    proxyService.submit(()->fetchGet(fullRequest, ctx, url));
  } else {
    // 不通过
    handleResponse(fullRequest, ctx, null);
  }
}
private void handleResponse(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, final HttpResponse endpointResponse) throws Exception {
  FullHttpResponse response = null;
  try {
    if(endpointResponse == null) {
      // 不通过响应
      String value = "hello,filter reject without hello";
      response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
      response.headers().set("Content-Type", "application/json");
      response.headers().setInt("Content-Length", response.content().readableBytes());
    } else {
      // 通过请求后响应
      byte[] body = EntityUtils.toByteArray(endpointResponse.getEntity());
      response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
      response.headers().set("Content-Type", "application/json");
      response.headers().setInt("Content-Length", Integer.parseInt(endpointResponse.getFirstHeader("Content-Length").getValue()));
    }
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
public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx, HttpRequestFilter filter) throws Exception {
  // 请求路由选择
  String backendUrl = router.route(this.backendUrls);
  final String url = backendUrl + fullRequest.uri();
  if (filter.filter(fullRequest, ctx)){
    proxyService.submit(()->fetchGet(fullRequest, ctx, url));
  } else {
    handleResponse(fullRequest, ctx, null);
  }
}
```

### 作业5

多线程示例

> DaemonThread示例（守护线程）
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/concurrency/conc01/DaemonThread.java
>
> JVM不会等待守护线程执行完毕再退出，当最后一个非*守护线程*结束时,*守护线程*随着JVM一同结束工作。
>
> The Java Virtual Machine exits when the only threads running are all daemon threads.
>
> 可以通过thread.setDaemon(true)来设置

> RunnerMain示例（线程中断，线程组）
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/concurrency/conc01/RunnerMain.java
>
> 1.线程中断
>
> ```java
> // 主线程逻辑
> Runner2 runner2 = new Runner2();
> Thread thread2 = new Thread(runner2);
> thread2.start(); // 主线程启动子线程
> thread2.interrupt(); // 主线程中断子线程，仅通知子线程中断，不会实际中断子线程，子线程可以根据isInterrupted接口判断来决定是否中断自己
> // 子线程逻辑，Runner2中
> ...... // 子线程处理逻辑，在执行过程中，外部主线程调用子线程的interrupt中断接口
> Thread.currentThread().isInterrupted(); // 子线程中断状态为 true
> Thread.interrupted(); // 判断当前子线程中断状态 true，并重置中断状态
> Thread.currentThread().isInterrupted(); // 重置后，当前子线程中断状态为 false
> ```
>
> 2.线程组相关
>
> ```java
> System.out.println(Thread.activeCount());
> Thread.currentThread().getThreadGroup().list();// main线程组
> System.out.println(Thread.currentThread().getThreadGroup().getParent().activeGroupCount());
> Thread.currentThread().getThreadGroup().getParent().list();// system线程组
> ```

> ThreadMain示例（3种线程启动方式）
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/concurrency/conc01/ThreadMain.java
>
> 在不进行任何线程间协同控制的情况下，线程执行完的顺序不可控
>
> 1.Thread方式启动
>
> ```java
> ThreadA threadA = new ThreadA();
> threadA.start();
> ```
>
> 2.Runnable方式启动
>
> ```java
> ThreadB threadB = new ThreadB();
> new Thread(threadB).start();
> ```
>
> 3.Callable方式启动（带返回值）
>
> ```java
> ThreadC threadC = new ThreadC();
> FutureTask<String> futureTask = new FutureTask<>(threadC);
> new Thread(futureTask).start();
> try {
>   // 主线程等待返回结果
> 	System.out.println("得到的返回结果是:" + futureTask.get());
> } catch (InterruptedException e) {
> 	e.printStackTrace();
> } catch (ExecutionException e) {
> 	e.printStackTrace();
> }
> ```

> 线程协作Join Wait/Notify示例
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/concurrency/conc01/op/
>
> thread1.join仅会释放thread1的锁
>
> oo.wait仅会释放oo的锁，需要在锁定oo后（拥有oo监视器）才能调用
>
> oo.notify可以唤醒在oo上等待的线程，需要在锁定oo后（拥有oo监视器）才能调用
>
> oo.wait在接收到oo.notify后，会重新尝试获取oo的锁

> 线程同步控制示例（使用synchronized控制同步块，方法，对象）
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/concurrency/conc01/sync/
>
> 通过synchronized实现临界区的线程安全访问

> 线程池示例
>
> https://github.com/piercebn/JavaCourse/blob/main/01jvm/java-cource/src/main/java/com/piercebn/javacource/concurrency/conc02/threadpool/
>
> 1.获取任务返回值结果（通过submit方式提交Callable任务）
>
> ```java
> ScheduledExecutorService executorService = Executors.newScheduledThreadPool(16);
> try {
>     String str = executorService.submit(new Callable<String>() {
>         @Override
>         public String call() throws Exception {
>             return "I am a task, which submited by the so called laoda, and run by those anonymous workers";
>         }
>     }).get();
> 
>     System.out.println("str=" + str);
> } catch (Exception e) {
>     e.printStackTrace();
> }
> ```
>
> 2.异常捕获
>
> ```java
> ExecutorService executorService = Executors.newFixedThreadPool(1);
> try {
>   Future<Double> future = executorService.submit(() -> {
>     throw new RuntimeException("executorService.submit()");
>   });
> 
>   double b = future.get();
>   System.out.println(b);
> 
> } catch (Exception ex) {
>   System.out.println("catch submit"); // 可以捕获通过submit提交任务中抛出的异常
>   ex.printStackTrace();
> }
> 
> try {
>   executorService.execute(() -> {
>     throw new RuntimeException("executorService.execute()");
>   });
> } catch (Exception ex) {
>   System.out.println("catch execute"); // 无法捕获通过excute执行任务中抛出的异常
>   ex.printStackTrace();
> }
> 
> executorService.shutdown();
> System.out.println("Main Thread End!");
> ```
>
> 3.Executors提供的4种创建线程池的方法
>
> 3.1 单线程，串行顺序执行
>
> ```java
> ExecutorService executorService = Executors.newSingleThreadExecutor();
> 
> for (int i = 0; i < 10; i++) {
>     final int no = i;
>     executorService.execute(() -> {
>         System.out.println("start:" + no);
>         try {
>             Thread.sleep(1000L);
>         } catch (InterruptedException e) {
>             e.printStackTrace();
>         }
>         System.out.println("end:" + no);
>     });
> }
> executorService.shutdown();
> System.out.println("Main Thread End!");
> ```
>
> 3.2 固定大小线程池，指定固定线程并行执行任务，其他任务在队列中等待
>
> ```java
> ExecutorService executorService = Executors.newFixedThreadPool(16);
> for (int i = 0; i < 100; i++) {
>     final int no = i;
>     executorService.execute(() -> {
>         try {
>             System.out.println("start:" + no);
>             Thread.sleep(1000L);
>             System.out.println("end:" + no);
>         } catch (InterruptedException e) {
>             e.printStackTrace();
>         }
>     });
> }
> executorService.shutdown(); // 停止接收新任务，原来的任务继续执行
> System.out.println("Main Thread End!");
> ```
>
> 3.3 可缓存的线程池，线程池大小不做限制
>
> ```java
> ExecutorService executorService = Executors.newCachedThreadPool();
> 
> for (int i = 0; i < 10000; i++) {
>     final int no = i;
>     Runnable runnable = new Runnable() {
>         @Override
>         public void run() {
>             try {
>                 System.out.println("start:" + no);
>                 Thread.sleep(1000L);
>                 System.out.println("end:" + no);
>             } catch (InterruptedException e) {
>                 e.printStackTrace();
>             }
>         }
>     };
>     executorService.execute(runnable);
> }
> executorService.shutdown();
> System.out.println("Main Thread End!");
> ```
>
> 3.4 可定时执行或周期性执行任务的线程池，线程池大小不做限制
>
> ```java
> ScheduledExecutorService executorService = Executors.newScheduledThreadPool(16);
> 
> for (int i = 0; i < 100; i++) {
>     final int no = i;
>     Runnable runnable = new Runnable() {
>         @Override
>         public void run() {
>             try {
>                 System.out.println("start:" + no);
>                 Thread.sleep(1000L);
>                 System.out.println("end:" + no);
>             } catch (InterruptedException e) {
>                 e.printStackTrace();
>             }
>         }
>     };
>     // 10s后执行
>     executorService.schedule(runnable, 10, TimeUnit.SECONDS);
> }
> executorService.shutdown();
> System.out.println("Main Thread End!");
> ```

### 作业6

完善网关的例子，调整线程池参数，优化性能











