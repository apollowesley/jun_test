package com.kind.samples.core.cas;

public class Counter {

    public static int count = 0;

    public static void inc() {
        try {
            Thread.sleep(1);
            count++;
        } catch (InterruptedException e) {

        }

    }

    public static void main(String[] args) {
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Counter.inc();
                }
            }).start();
        }
        //这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:MyCounter.count=" + Counter.count);

    }
}