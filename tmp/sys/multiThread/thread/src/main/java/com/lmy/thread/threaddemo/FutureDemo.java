package com.lmy.thread.threaddemo;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureDemo {
	public static void main(String[] args) {
		String aa="123123";
		ExecutorService executor=Executors.newCachedThreadPool();
		FutureDemo_task task=new FutureDemo_task();
		Future<Integer> result=executor.submit(task);
		executor.shutdown();
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("���߳���ִ������");
		try {
			System.out.println("task���н��"+result.get());
		} catch (Exception e) {
			// TODO: handle exception
		}
		System.out.println("��������ִ�����");
	}
}
