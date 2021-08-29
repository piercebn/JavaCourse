package com.piercebn.javacource.concurrency.conc01;

import java.util.concurrent.Callable;

public class ThreadC implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("这是线程C开始运行");
        Thread.sleep(500);
        System.out.println("这是线程C: " + Thread.currentThread().getName());
        return "线程C";
    }

}
