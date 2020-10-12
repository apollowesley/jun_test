package com.lmy.thread.tb6;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;

public class SemaphoreDemo {
	public void method(Semaphore sema){
		try {
			sema.acquire();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(Thread.currentThread().getName()+" 开始执行...");
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sema.release();
	}
	
	public static void main(String[] args) {
		SemaphoreDemo d=new SemaphoreDemo();
		Semaphore sema=new Semaphore(10);
		ExecutorService pool=Executors.newFixedThreadPool(10);
		while(true){
			pool.execute(new Runnable() {
				
				@Override
				public void run() {
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread().getName()+" 正在执行");
				}
			});
		}
//		while(true){
//			new Thread(new Runnable() {
//				@Override
//				public void run() {
//					d.method(sema);
//					try {
//						Thread.sleep(500);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}).start();
//		}
	}
}
