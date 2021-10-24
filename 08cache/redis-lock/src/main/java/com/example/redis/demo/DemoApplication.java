package com.example.redis.demo;

import com.example.redis.demo.lock.RedisInvertory;
import com.example.redis.demo.lock.RedisLock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    private final static String LOCK_KEY = "redis_lock";

    private final static int EXPIRE = 30;

    private final static String INVENTORY_KEY = "redis_inventory";

    private final static int INIT_AMOUNT = 10;

    public static void lockTest() {
        System.out.println("lock test:: start with " + Thread.currentThread().getName());

        if (!RedisLock.getInstance().lock(LOCK_KEY, Thread.currentThread().getName(), EXPIRE)) {
            System.out.println("lock test:: lock fail with " + Thread.currentThread().getName());
            return;
        }
        System.out.println("lock test:: lock with " + Thread.currentThread().getName());

        try {
            Thread.sleep(10000);
            RedisInvertory.getInstance().reduce(INVENTORY_KEY);
            System.out.printf("库存减一 amount: %d\n", RedisInvertory.getInstance().get(INVENTORY_KEY));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        RedisLock.getInstance().release(LOCK_KEY, Thread.currentThread().getName());
        System.out.println("lock test:: release lock with " + Thread.currentThread().getName());
    }

    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(DemoApplication.class, args);

        System.out.println("application start and init inventory");
        RedisInvertory.getInstance().init(INVENTORY_KEY, INIT_AMOUNT);

        Thread thread1 = new Thread(DemoApplication::lockTest);
        Thread thread2 = new Thread(DemoApplication::lockTest);
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        Thread thread3 = new Thread(DemoApplication::lockTest);
        thread3.start();
        thread3.join();
        System.out.println("application end");
    }

}
