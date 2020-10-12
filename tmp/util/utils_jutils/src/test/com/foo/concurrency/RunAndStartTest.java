package com.foo.concurrency;

import org.junit.Test;

/**
 * run() start()区别
 * @author Dean
 */
public class RunAndStartTest {

    private class MyThread implements Runnable{
        @Override
        public void run() {
            for(int i=0;i<5;i++){
                System.out.println(i);
            }
        }
    }

    /**
     * 一般启动线程都是调用start()方法，调用start()方法后会立即返回，一个新的线程会启动并执行MyThread的run()方法，
     * 而执行test()方法的主线程会立即继续往下执行
     */
    @Test
    public void test(){
        Thread thread = new Thread(new MyThread());
        thread.start();
        System.out.println("---main thread---");//不会在最后执行
    }

    /**
     * 如果调用run()方法，将不会新启一个线程执行而会被执行test1()方法的主线程执行
     */
    @Test
    public void test1(){
        Thread thread1 = new Thread(new MyThread());
        thread1.run();
        System.out.println("---main thread---");//会最后执行
    }
}
