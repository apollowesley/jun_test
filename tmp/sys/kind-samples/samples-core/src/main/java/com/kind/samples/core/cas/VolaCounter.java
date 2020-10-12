package com.kind.samples.core.cas;

/**
 * Created by weiguo.liu on 2017/1/4.
 */
public class VolaCounter {
    public volatile static int count = 0;

    public static void inc() {
        try {
            Thread.sleep(1);
            ++count;
        } catch (Exception e) {

        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    VolaCounter.inc();
                }
            }).start();
        }
        System.out.printf("VolaCount.count:" + VolaCounter.count);
    }
}
