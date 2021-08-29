package com.piercebn.javacource.concurrency.homework;

import java.util.concurrent.*;

/**
 * 方法8：用线程池FutureTask+Callable
 */
public class Homework08 {

    public static void main(String[] args) {

        long start=System.currentTimeMillis();

        int result = 0;

        FutureTask<Integer> task = new FutureTask<Integer>(()->{
            return sum();
        });
        new Thread(task).start();

        try {
            result = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

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
