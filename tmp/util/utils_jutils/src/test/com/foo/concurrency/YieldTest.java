package com.foo.concurrency;

import org.junit.Test;

/**
 *
 * @author Dean
 */
public class YieldTest {

    private class YieldThread extends Thread{

        YieldThread(String name){
            super(name);
        }

        @Override
        public void run() {
            for(int i = 0;i < 6;i++){
                System.out.println(this.getName() + "---" + i);
                if(i == 3){
                    //让步，建议具有相同优先级的其他线程可以运行，但没有任何机制保证会被采纳 《Think.in.java(4th)》p661
                    Thread.yield();
                    //应该使用Thread.sleep(1)替代Thread.yield()来进行并发测试 《Effective.Java(2nd)》p253
                }
            }
        }
    }

    @Test
    public void test(){
        new YieldThread("thread1").start();
        new YieldThread("thread2").start();
    }

    /* 情况1：thread1循环到3的时候让掉CPU，thread2抢到CPU
    thread1---0
    thread1---1
    thread1---2
    thread1---3
    thread2---0
    thread2---1
    thread2---2
    thread2---3
    */

    /* 情况2：thread1循环到3的时候让掉CPU，thread1抢到CPU
    thread1---0
    thread1---1
    thread1---2
    thread1---3
    thread1---4
    thread1---5
    thread2---0
    thread2---1
    thread2---2
    thread2---3
    */
}
