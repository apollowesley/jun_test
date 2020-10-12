package com.kind.samples.core.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 通过线程池实现多线程
 *
 * @author cary
 * @version 1.0.0
 * @date 2015-9-16-下午3:18:23
 * @Copyright © 2015 www.kindapp.net
 */
public class MyThreadPool {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        ExecutorService threadPool1 = Executors.newFixedThreadPool(5);
        ExecutorService threadPool2 = Executors.newCachedThreadPool();
        ExecutorService threadPool = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int task = i;
            threadPool.execute(new Runnable() {

                @Override
                public void run() {
                    for (int j = 1; j <= 10; j++) {
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println(Thread.currentThread().getName()
                                + " is looping of " + j + " for  task of "
                                + task);
                    }

                }
            });
        }

        System.out.println("all of 10 tasks have committed! ");
        // threadPool.shutdownNow();

        Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable() {
            public void run() {
                System.out.println("begining!");

            }
        }, 6, 2, TimeUnit.SECONDS);
    }

}
