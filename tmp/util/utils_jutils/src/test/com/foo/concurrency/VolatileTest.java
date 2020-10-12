package com.foo.concurrency;

/**
 * volatile之变量对所有线程的可见性测试 模仿《Java并发编程实战》程序清单3-1
 * volatile作用：变量对所有线程的可见性；禁止指令重排序优化（见《JAVA20年.道路与梦想》双重检查锁定与延迟初始化）；
 * 基于volatile变量的运算在并发下不是安全的，见TestAtomicThread
 *
 * @author Dean
 */
public class VolatileTest {
    private static boolean ready = false;
//	private volatile static boolean ready;

    //读线程
    private static class ReaderThread extends Thread {
        public void run() {
            while (!ready) {
//            	System.out.println("1"); //个人分析由于println()方法内部的代码synchronized(this)让线程能正确看到ready变量的改变
            }
        }
    }

    // ready变量不加上volatile且while循环里不打印，程序将陷入死循环
    // ready变量加上volatile或者while循环里进行打印，ReaderThread能够读取到主线程对ready的更改而结束掉循环
    // 把ready的读和写封装成两个加了synchronized锁的方法并在读取和写入的地方调用，也可以避免死循环《Effective.Java(2nd)》p231
    public static void main(String[] args) {
        new ReaderThread().start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //主线程 写入值
        ready = true;
    }

}
