package com.kind.samples.core.threads;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁测试。
 *
 * @author cary
 * @version 1.0.0
 * @date 2015-8-24-下午5:27:50
 */
public class MyReadWriteLock {
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

    public static void main(String[] args) {
        final MyReadWriteLock test = new MyReadWriteLock();
        /**
         * 线程1
         */
        new Thread(new Runnable() {

            public void run() {
                test.get(Thread.currentThread());
            }
        }).start();
        /**
         * 线程2
         */
        new Thread() {
            public void run() {
                test.get(Thread.currentThread());
            }

            ;
        }.start();

    }

    /**
     * 使用读写锁使用.
     *
     * @param thread
     */
    public void get(Thread thread) {
        rwl.readLock().lock();
        try {
            long start = System.currentTimeMillis();

            while (System.currentTimeMillis() - start <= 0.5) {
                System.out.println(thread.getName() + "正在进行读操作");
            }
            System.out.println(thread.getName() + "读操作完毕");
        } finally {
            rwl.readLock().unlock();
        }
    }
}
