package com.kind.samples.core.threads;

/**
 * synchronized读写锁效果
 *
 * @author cary
 * @version 1.0.0
 * @date 2015-8-24-下午5:18:14
 */
public class SynchronizedLock {
    public static void main(String[] args) {
        final SynchronizedLock test = new SynchronizedLock();
        /**
         * 第一个线程
         */
        new Thread(new Runnable() {
            public void run() {
                test.get(Thread.currentThread());
            }
        }).start();
        /**
         * 第二个线程
         */
        new Thread(new Runnable() {

            public void run() {
                test.get(Thread.currentThread());
            }
        }).start();

    }

    /**
     * synchronized成员方法锁成
     *
     * @param thread
     */
    public synchronized void get(Thread thread) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start <= 1) {
            System.out.println(thread.getName() + "正在进行读操作");
        }
        System.out.println(thread.getName() + "读操作完毕");
    }
}