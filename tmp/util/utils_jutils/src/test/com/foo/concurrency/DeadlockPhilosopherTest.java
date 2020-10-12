package com.foo.concurrency;

import org.junit.Test;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 死锁经典问题-哲学家进餐问题 例子来自于《Think.in.java(4th)》p719 上面提供了死锁必须同时满足的四个条件
 * 《Java并发编程的艺术》1.2 介绍了避免死锁的四个常见方式
 * @author Dean
 */
public class DeadlockPhilosopherTest {
    /**
     * 筷子
     */
    private class Chopstick{
        private boolean taken = false; //筷子是否被拿起
        /**
         * 筷子被拿起
         */
        synchronized void take() throws InterruptedException{
            while (taken){
                wait();
            }
            taken = true;
        }

        /**
         * 筷子被放下
         */
        synchronized void drop(){
            taken = false;
            notifyAll();
        }
    }

    /**
     * 哲学家
     */
    private class Philosopher implements Runnable{
        private int id;
        private Chopstick left;
        private Chopstick right;
        private int factor; //睡眠的时间因子
        private Random random = new Random(47);

        Philosopher(Chopstick left,Chopstick right,int id,int factor){
            this.left = left;
            this.right = right;
            this.id = id;
            this.factor = factor;
        }

        private void thinking() throws InterruptedException{
            if(factor == 0) return;
            System.out.println(this + "开始思考");
            TimeUnit.MILLISECONDS.sleep(random.nextInt(factor * 250));
        }

        private void eating() throws InterruptedException{
            if(factor == 0) return;
            System.out.println(this + "开始吃饭");
            TimeUnit.MILLISECONDS.sleep(random.nextInt(factor * 250));
        }



        @Override
        public String toString() {
            return "哲学家" + id + " ";
        }

        @Override
        public void run() {
            try{
                while(!Thread.interrupted()){
                    thinking();
                    System.out.println(this + "拿右手筷子");
                    right.take();
                    System.out.println(this + "拿左手筷子");
                    left.take();
                    eating();
                    right.drop();
                    left.drop();
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Test
    public void test() throws InterruptedException {
        int size = 5;//哲学家人数
        int factor = 0; //因子为0会让死锁尽快发生

        ExecutorService executorService = Executors.newCachedThreadPool();
        Chopstick[] chopsticks = new Chopstick[size];
        for(int i = 0;i < size;i++){
            chopsticks[i] = new Chopstick();
        }

        for(int i = 0;i < size;i++){
            //如果最后一个Philosopher被初始化成先拿左边的筷子再拿右边的筷子，则可以破坏循环等待这个死锁条件
            Philosopher philosopher = new Philosopher(chopsticks[i], chopsticks[(i + 1) % size], i, factor);
            executorService.execute(philosopher);
        }

        try {
            TimeUnit.SECONDS.sleep(60);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
    }

}
