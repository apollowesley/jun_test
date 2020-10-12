package com.kind.samples.core.cas;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by weiguo.liu on 2017/1/4.
 */
public class AtomicCounter {
    public static AtomicInteger count = new AtomicInteger(0);

    public static void inc() {
        try {
            Thread.sleep(1);
            count.incrementAndGet();
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    AtomicCounter.inc();
                }
            }).start();
        }
        System.out.println("AtomicCount.count:" + AtomicCounter.count);
    }

}
