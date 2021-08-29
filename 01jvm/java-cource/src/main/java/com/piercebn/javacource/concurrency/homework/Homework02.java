package com.piercebn.javacource.concurrency.homework;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 方法2：用wait和notify机制
 */
public class Homework02 {

    public static void main(String[] args) {

        long start=System.currentTimeMillis();

        final AtomicInteger result = new AtomicInteger();
        final Object oo = new Object();

        Thread thread = new Thread(()->{
            result.set(sum());
            synchronized (oo) {
                oo.notify();
            }
        });
        thread.start();
        synchronized (oo) {
            try {
                oo.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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
