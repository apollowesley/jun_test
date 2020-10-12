package com.lmy.thread.threaddemo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class FutureTaskDemo {
	public static void main(String[] args) {
		//第一种方式
//		ExecutorService executor=Executors.newCachedThreadPool();
//		FutureDemo_task task=new FutureDemo_task();
//		FutureTask<Integer> futureTask=new FutureTask<Integer>(task);
//		executor.submit(futureTask);
//		executor.shutdown();
		//第二种方式
		FutureDemo_task task=new FutureDemo_task();
		FutureTask<Integer> futureTask=new FutureTask<Integer>(task);
		futureTask.run();
//		Thread t=new Thread(futureTask);
//		t.start();
		try {
//			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("主线程在执行任务");
		try {
			System.out.println("task运行结果"+futureTask.get());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("所有任务执行完毕");
	}
}
