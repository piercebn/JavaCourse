package com.piercebn.javacource.concurrency.conc01;

public class ThreadA extends Thread {

    @Override
    public void run() {
        System.out.println("这是线程A开始运行");
        super.run();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("这是线程A: " + Thread.currentThread().getName());
    }
}
