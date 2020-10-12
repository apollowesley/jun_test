package com.kind.samples.core.threads;

/**
 * 类锁与静态方法锁
 *
 * @author cary
 */
public class ClassSynchronized {
    /**
     * 类锁
     */
    public void testClassLock() {
        synchronized (ClassSynchronized.class) {
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

    public static synchronized void testStaticLock() {
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
     * 普通方法
     */
    public synchronized void testNormal() {
        int i = 5;
        while (i-- > 0) {
            System.out.println("normal-" + Thread.currentThread().getName()
                    + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
        }
    }

    /**
     * 静态方法
     */
    public synchronized void testStaticNormal() {
        int i = 5;
        while (i-- > 0) {
            System.out.println("static-" + Thread.currentThread().getName()
                    + " : " + i);
            try {
                Thread.sleep(500);
            } catch (InterruptedException ie) {
            }
        }
    }

    /**
     * 测试synchronized锁的互斥效果
     *
     * @param args
     */
    public static void main(String[] args) {
        final ClassSynchronized test = new ClassSynchronized();
        Thread testClass = new Thread(new Runnable() {
            public void run() {
                test.testClassLock();
            }
        }, "testClassLock");
        Thread testStatic = new Thread(new Runnable() {
            public void run() {
                ClassSynchronized.testStaticLock();
            }
        }, "testStaticLock");
        /**
         * 线程1
         */
        testClass.start();
        /**
         * 线程2
         */
        testStatic.start();
        /**
         * 成员方法
         */
        test.testNormal();
        /**
         * 静态方法
         */
        ClassSynchronized.testStaticLock();

    }
}
