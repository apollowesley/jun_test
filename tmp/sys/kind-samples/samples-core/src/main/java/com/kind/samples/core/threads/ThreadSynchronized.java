package com.kind.samples.core.threads;

/**
 * 多线程使用Synchronized锁
 *
 * @author Cary
 * @version 1.0.0
 * @Copyright © 2015 www.kindapp.net
 */
public class ThreadSynchronized {

    /**
     * @param args
     */
    public static void main(String[] args) {
        new ThreadSynchronized().init();
    }

    private void init() {
        final Outputer outputer = new Outputer();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputer.output("zhangsan");
                }

            }
        }).start();

        new Thread(new Runnable() {
            @SuppressWarnings("static-access")
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outputer.output3("lisi");
                }

            }
        }).start();

    }

    static class Outputer {

        /**
         * 同步代码块
         *
         * @param name
         */
        public void output(String name) {
            int len = name.length();
            synchronized (Outputer.class) {
                for (int i = 0; i < len; i++) {
                    System.out.print(name.charAt(i));
                }
                System.out.println();
            }
        }

        /*
         * 同步成员方法
         */
        public synchronized void output2(String name) {
            int len = name.length();
            for (int i = 0; i < len; i++) {
                System.out.print(name.charAt(i));
            }
            System.out.println();
        }

        /**
         * 同步静态方法
         *
         * @param name
         */
        public static synchronized void output3(String name) {
            int len = name.length();
            for (int i = 0; i < len; i++) {
                System.out.print(name.charAt(i));
            }
            System.out.println();
        }
    }
}
