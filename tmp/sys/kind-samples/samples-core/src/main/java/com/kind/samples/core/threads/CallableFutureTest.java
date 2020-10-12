package com.kind.samples.core.threads;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by cary on 2017/1/6.
 */
public class CallableFutureTest {
    public static void main(String[] args) throws Exception {
        CallableFutureTest test = new CallableFutureTest();
        //创建线程池
        ExecutorService pool = Executors.newFixedThreadPool(2);
        //创建带返回值的线程
        Callable c1 = test.new MyCallable("A");
        Callable c2 = test.new MyCallable("B");

        //执行任务并获取future对象
        Future f1 = pool.submit(c1);
        Future f2 = pool.submit(c2);
        // 从Future对象上获取任务的返回值，并输出到控制台
        System.out.println("----" + f1.get().toString());
        System.out.println("----" + f2.get().toString());
        pool.shutdown();

    }

    class MyCallable implements Callable {
        private String name;

        MyCallable(String name) {
            this.name = name;
        }

        public Object call() throws Exception {
            return name + "任务返回的内容";
        }
    }
}
