package com.lmy.thread.t2;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;


public class Demo4 implements Callable<Integer>{

	public Integer call() throws Exception {
		System.out.println("正在进行紧张的计算中。。。");
		Thread.sleep(3000);
		return 1;
	}
	
	public static void main(String[] args) throws InterruptedException, ExecutionException {
		Demo4 demo4=new Demo4();
		FutureTask<Integer> task=new FutureTask<Integer>(demo4);
		Thread t=new Thread(task);
		t.start();
		System.out.println("我先干点别的");
		Integer result=task.get();
		System.out.println("线程执行的结果为："+result);
	}

}
