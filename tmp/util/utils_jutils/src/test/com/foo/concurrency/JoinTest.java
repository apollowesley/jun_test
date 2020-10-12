package com.foo.concurrency;

import org.junit.Test;

/**
 * join 例子来自于《Think.in.java(4th)》p670
 * @author Dean
 */
public class JoinTest {

    private class Sleeper extends Thread{
        private int sleepTime;
        Sleeper(String name,int sleepTime){
            super(name);
            this.sleepTime = sleepTime;
            start();
        }
        @Override
        public void run(){
            try{
                sleep(sleepTime);
            }catch (InterruptedException e){
                System.out.println(getName() + " was interrupted.");
                return;
            }
            System.out.println(getName() + " has awakened");
        }
    }

    private class Joiner extends Thread{
        private Sleeper sleeper;
        Joiner(String name, Sleeper sleeper){
            super(name);
            this.sleeper = sleeper;
            start();
        }
        @Override
        public void run(){
            try{
                sleeper.join(); //线程Joiner在线程sleeper上调用sleeper.join()，线程Joiner将被挂起，直到目标线程sleeper结束才恢复
            }catch (InterruptedException e){
                e.printStackTrace();
            }
            System.out.println(getName() + " join completed.");
        }
    }

    @Test
    public void test(){
        Sleeper sleeper1 = new Sleeper("sleeper1",1500);
        Sleeper sleeper2 = new Sleeper("sleeper2",1500);
        new Joiner("joiner1",sleeper1);
        new Joiner("joiner2",sleeper2);
        sleeper2.interrupt();

        try {
            Thread.sleep(2000); //避免上面的输出语句还没有输出完主线程就退出了
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
