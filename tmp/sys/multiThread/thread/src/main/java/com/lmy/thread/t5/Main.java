package com.lmy.thread.t5;

import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
	public static void main(String[] args) {
//		Singleton singleton=Singleton.getInstance();
//		Singleton singleton1=Singleton.getInstance();
//		System.out.println(singleton==singleton1);
		
		ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(20);
		for (int i = 0; i < 20; i++) {
			newFixedThreadPool.execute(new Runnable(){
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName()+Singleton3.getInstance());
				}
			});
		}
		newFixedThreadPool.shutdown();
	}
}
