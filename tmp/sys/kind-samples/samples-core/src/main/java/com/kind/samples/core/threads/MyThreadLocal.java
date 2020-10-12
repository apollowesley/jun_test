package com.kind.samples.core.threads;

import java.util.Random;

/**
 * ThreadLocal测试
 *
 * @author cary
 * @version 1.0.0
 * @date 2015-8-24-下午6:06:01
 */
public class MyThreadLocal {
    private static ThreadLocal<Integer> x = new ThreadLocal<Integer>();

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            new Thread(new Runnable() {
                public void run() {
                    int data = new Random().nextInt();
                    System.out.println(Thread.currentThread().getName()
                            + " has put data :" + data);
                    x.set(data);

                    new A().get();
                    new B().get();
                }
            }).start();
        }
    }

    /**
     * 获取数据
     *
     * @author cary
     * @version 1.0.0
     * @date 2015-8-24-下午6:05:27
     */
    static class A {
        public void get() {
            int data = x.get();
            System.out.println("A from " + Thread.currentThread().getName()
                    + " get data :" + data);

        }
    }

    /**
     * 获取数据
     *
     * @author cary
     * @version 1.0.0
     * @date 2015-8-24-下午6:05:44
     */
    static class B {
        public void get() {
            int data = x.get();
            System.out.println("B from " + Thread.currentThread().getName()
                    + " get data :" + data);

        }
    }
}
