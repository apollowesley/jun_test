package com.lmy.thread.threaddemo;

import java.util.concurrent.Callable;

public class FutureDemo_task implements Callable<Integer>{

	@Override
	public Integer call() throws Exception {
		System.out.println("���߳����ڽ��м���");
		Thread.sleep(3000);
		int sum=0;
		for(int i=0;i<100;i++){
			sum+=i;
		}
		return sum;
	}

}
