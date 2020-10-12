package com.kind.samples.core.threads;

/**
 * 锁普通方法和静态方法。
 *
 * @author cary
 */
public class MethodSynchronized {
    /**
     * 锁普通方法
     */
    public synchronized void testNormal() {
        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
        }
    }

    /**
     * 锁静态方法
     */
    public static synchronized void testStatic() {
        int i = 5;
        while (i-- > 0) {
            System.out.println(Thread.currentThread().getName() + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
        }
    }

    /**
     * 普通方法和静态方法
     *
     * @param args
     */
    public static void main(String[] args) {
        final ObjectBlockSynchronized test = new ObjectBlockSynchronized();
        Thread test1 = new Thread(new Runnable() {
            public void run() {
                test.testNormal();
            }
        }, "testNormal");
        Thread test2 = new Thread(new Runnable() {
            public void run() {
                MethodSynchronized.testStatic();
            }
        }, "testStatic");
        /**
         * 启动普通方法线程
         */
        test1.start();
        /**
         * 启动静态方法线程
         */
        test2.start();

    }
}
