package com.kind.samples.core.threads.lock;

import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * lock锁的应用
 *
 * @author cary
 * @version 1.0.0
 * @date 2015-8-24-下午5:01:45
 */
public class MyLock {
    private ArrayList<Integer> arrayList = new ArrayList<Integer>();

    public static void main(String[] args) {
        final MyLock test = new MyLock();

        /**
         * 创建第一个线程
         */
        new Thread(new Runnable() {

            public void run() {
                test.getLock(Thread.currentThread());
            }
        }).start();

        /**
         * 创建第二个线程
         */
        new Thread() {
            public void run() {
                test.getLock(Thread.currentThread());
            }

            ;
        }.start();
    }

    /**
     * 获得锁
     *
     * @param thread
     */
    public void getLock(Thread thread) {
        /**
         * 实例化锁对象
         */
        Lock lock = new ReentrantLock();
        lock.lock();
        try {
            System.out.println(thread.getName() + "得到了锁");
            for (int i = 0; i < 5; i++) {
                arrayList.add(i);
            }
        } catch (Exception e) {
        } finally {
            System.out.println(thread.getName() + "释放了锁");
            lock.unlock();
        }
    }
}
