package com.foo.concurrency;

import org.junit.Test;

/**
 * 编写线程的几种方式
 * @author Dean
 */
public class HelloWorldTest {

    /**
     * 实现Runnable接口
     */
    @Test
    public void test1(){
        Thread thread = new Thread(new MyThread1());
        thread.start();
    }

    /**
     * 继承Thread
     */
    @Test
    public void test2(){
        new MyThread2().start();
    }

    /**
     * 匿名内部类
     */
    @Test
    public void test3(){
        new Thread(){
            @Override
            public void run() {
                System.out.println("MyThread3 inner class");
            }
        }.start();
    }

    /**
     * Lambda
     */
    @Test
    public void test4(){
        new Thread(()->{
            System.out.println("MyThread4 Lambda");
        }).start();
    }


    private class MyThread1 implements Runnable{
        @Override
        public void run() {
            System.out.println("MyThread1 implements Runnable");
        }
    }

    private class MyThread2 extends Thread{
        @Override
        public void run() {
            System.out.println("MyThread2 extends Thread");
        }

    }
}
