package com.piercebn.javacource.concurrency.homework;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 方法10：用Condition机制
 */
public class Homework10 {

    public static void main(String[] args) {

        long start=System.currentTimeMillis();

        final AtomicInteger result = new AtomicInteger();
        final Lock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();

        Thread thread = new Thread(()->{
            result.set(sum());
            lock.lock();
            condition.signal();
            lock.unlock();
        });
        thread.start();

        lock.lock();
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();

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
