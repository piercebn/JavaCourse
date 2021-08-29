package com.piercebn.javacource.concurrency.homework;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 方法4：用线程池submit+Runnable
 */
public class Homework04 {

    public static void main(String[] args) {

        long start=System.currentTimeMillis();

        final AtomicInteger result = new AtomicInteger();

        ExecutorService executorService = Executors.newFixedThreadPool(1);

        try {
            Future future = executorService.submit(() -> {
                result.set(sum());
            });
            future.get();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result.get());

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
        executorService.shutdown();
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2) {
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }
}
