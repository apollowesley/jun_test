package com.foo.concurrency;

import org.junit.Test;

/**
 * 例子来自《Struts2技术内幕》4.1.4
 * @author Dean
 */
public class ThreadLocalTest {
    //ThreadLocal实例通常是类中的private static属性
    private static ThreadLocal<Integer> threadLocal = new ThreadLocal<Integer>(){
        @Override
        protected Integer initialValue() {//初始化value，看ThreadLocal源码可知什么时候会调用此方法
            return 10;
        }
    };
    private static Integer get(){
        return threadLocal.get();
    }
    private static void set(Integer value){
        threadLocal.set(value);
    }

    private static Integer getNext(){
        set(get() + 1);
        return get();
    }

    private class ThreadLocalTestThread extends Thread{
        @Override
        public void run() {
            for(int i = 0;i < 3;i++){
                System.out.println("Thread["+Thread.currentThread().getName()+"],count="+getNext());
            }
        }
    }

    @Test
    public void test(){
        new ThreadLocalTestThread().start();
        new ThreadLocalTestThread().start();
        new ThreadLocalTestThread().start();
    }
}
