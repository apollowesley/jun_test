package com.kind.samples.core.threads;

/**
 * 同步代码块与同步实例方法的互斥
 *
 * @author cary
 */
public class ObjectBlockSynchronized {
    /**
     * 同步代码块
     */
    public void testBlock() {
        synchronized (this) {
            int i = 5;
            while (i-- > 0) {
                System.out
                        .println(Thread.currentThread().getName() + " : " + i);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ie) {
                }
            }
        }
    }

    /**
     * 非同步普通方法
     */
    public void testNormal() {
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
     * 同步实例方法
     */
    public synchronized void testMethod() {
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
     * 主方法分别调用三个方法
     *
     * @param args
     */
    public static void main(String[] args) {
        final ObjectBlockSynchronized test = new ObjectBlockSynchronized();
        Thread test1 = new Thread(new Runnable() {
            public void run() {
                test.testBlock();
            }
        }, "testBlock");
        Thread test2 = new Thread(new Runnable() {
            public void run() {
                test.testMethod();
            }
        }, "testMethod");
        test1.start();
        ;
        test2.start();
        test.testNormal();
    }
}
