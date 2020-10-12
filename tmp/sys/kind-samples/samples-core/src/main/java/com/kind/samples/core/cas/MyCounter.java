package com.kind.samples.core.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class MyCounter {

    public static int count = 0;

    public static volatile int volatileCount = 0;
    public static AtomicInteger atomicCount = new AtomicInteger(0);

    public static byte[] lock = new byte[0];
    public static int syCount = 0;

    public static void sleep(final int gap) {
        try {
            Thread.sleep(gap);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void incrInt(final int gap) {
        count++;
        sleep(gap);
    }

    public static void incrVolatileInt(int gap) {
        /**
         * 和volatileCount上一个状态相关，不具于原子性
         */
        volatileCount++;
        sleep(gap);
    }

    public static void incrAtomicInt(final int gap) {
        // 第一种方式：
        // int tmp=atomicCount.get();
        // atomicCount.getAndSet(tmp+1);//注意：tmp+1不具原子性

        // 第二种方式:
        atomicCount.getAndIncrement();// 具备原子性的
        sleep(gap);
    }

    public static void incrSynInt(final int gap) {
        synchronized (lock) {
            syCount++;
            sleep(gap);
        }
    }

    public static void testInt(int count, final int gap) {
        long st = System.currentTimeMillis();
        Thread threads[] = new Thread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    MyCounter.incrInt(gap);
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < count; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        long et = System.currentTimeMillis();
        System.out.println("---运行时间＝" + (et - st));
        // 这里每次运行的值都有可能不同,可能为1000
        System.out.println("运行结果:MyCounter.count=" + MyCounter.count);
    }

    public static void testVolatileInt(int count, final int gap) {
        long st = System.currentTimeMillis();
        Thread threads[] = new Thread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    MyCounter.incrVolatileInt(gap);
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < count; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        long et = System.currentTimeMillis();
        System.out.println("---运行时间＝" + (et - st));
        System.out.println("运行结果:MyCounter.volatileCount="
                + MyCounter.volatileCount);
    }

    public static void testAtomicInt(int count, final int gap) {
        long st = System.currentTimeMillis();
        Thread threads[] = new Thread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    MyCounter.incrAtomicInt(gap);
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < count; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        long et = System.currentTimeMillis();
        System.out.println("---运行时间＝" + (et - st));
        System.out.println("运行结果:MyCounter.atomicCount="
                + MyCounter.atomicCount.get());
    }

    public static void testSynInt(int count, final int gap) {
        long st = System.currentTimeMillis();
        Thread threads[] = new Thread[count];
        for (int i = 0; i < count; i++) {
            threads[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    MyCounter.incrSynInt(gap);
                }
            });
            threads[i].start();
        }
        for (int i = 0; i < count; i++) {
            try {
                threads[i].join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long et = System.currentTimeMillis();
        System.out.println("---运行时间＝" + (et - st));
        System.out.println("运行结果:MyCounter.syCount=" + MyCounter.syCount);
    }

    public static void main(String[] args) {
        int cnt = 100000;
        int gap = 2;
        testInt(cnt, gap);
        System.out.println("-------------------------------------------");
        testVolatileInt(cnt, gap);
        System.out.println("-------------------------------------------");
        testAtomicInt(cnt, gap);
        System.out.println("-------------------------------------------");
        testSynInt(cnt, gap);
    }
}