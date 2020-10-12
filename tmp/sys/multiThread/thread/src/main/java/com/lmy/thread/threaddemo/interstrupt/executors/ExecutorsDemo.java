package com.lmy.thread.threaddemo.interstrupt.executors;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class ExecutorsDemo {
	public static void main(String[] args) {
		
		ExecutorService exec=Executors.newCachedThreadPool();
		Executors.unconfigurableExecutorService(exec);
		if(exec instanceof ThreadPoolExecutor){
			((ThreadPoolExecutor)exec).setCorePoolSize(10);
			System.out.println(((ThreadPoolExecutor) exec).getCorePoolSize());
		}else{
			throw new AssertionError("opps, bad assumption");
		}
	}
}
