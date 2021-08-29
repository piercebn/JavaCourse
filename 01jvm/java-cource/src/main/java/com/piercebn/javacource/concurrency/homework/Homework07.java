package com.piercebn.javacource.concurrency.homework;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 方法7：用CountDownLatch
 */
public class Homework07 {

    public static void main(String[] args) {

        long start=System.currentTimeMillis();

        final AtomicInteger result = new AtomicInteger();
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        Thread thread = new Thread(()->{
            result.set(sum());
            countDownLatch.countDown();
        });
        thread.start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result.get());

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
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
