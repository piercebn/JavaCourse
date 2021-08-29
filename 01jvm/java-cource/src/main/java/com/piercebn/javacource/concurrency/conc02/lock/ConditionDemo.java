package com.piercebn.javacource.concurrency.conc02.lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class ConditionDemo {
    final Lock lock = new ReentrantLock();
    final Condition notFull  = lock.newCondition();
    final Condition notEmpty = lock.newCondition();

    final Object[] items = new Object[20];
    int putptr, takeptr, count;

    public void put(Object x) throws InterruptedException {
        lock.lock();
        System.out.println("put获取lock");
        try {
            // 当count等于数组的大小时，当前线程等待，直到notFull通知，再进行生产
            while (count == items.length) {
                System.out.println("数组满了"+x);
                notFull.await();
            }
            items[putptr] = x;
            System.out.println("插入第"+putptr+"个"+x);
            if (++putptr == items.length) {
                putptr = 0;
            }
            ++count;
            notEmpty.signal();
            System.out.println("-插入后第"+putptr+"个，总数"+count);
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        System.out.println("take获取lock");
        try {
            // 当count为0，进入等待，直到notEmpty通知，进行消费。
            while (count == 0) {
                System.out.println("数组空了");
                notEmpty.await();
            }
            Object x = items[takeptr];
            System.out.println("获取第"+takeptr+"个"+x);
            if (++takeptr == items.length) {
                takeptr = 0;
            }
            --count;
            notFull.signal();
            System.out.println("-获取后第"+takeptr+"个，总数"+count);
            return x;
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        ConditionDemo demo = new ConditionDemo();



        for (int i = 0; i < 60; i++) {
            int finalI = i;
            new Thread() {
                @Override
                public void run() {
                    try {
                        demo.put(finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }

        for (int i = 0; i < 40; i++) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        demo.take();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }.start();
        }
    }
}
