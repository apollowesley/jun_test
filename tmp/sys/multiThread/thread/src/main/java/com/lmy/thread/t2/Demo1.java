package com.lmy.thread.t2;

public class Demo1 extends Thread{
	
	public Demo1(String name){
		super(name);
	}
	
	@Override
	public void run() {
		while(true){
			System.out.println(Thread.currentThread().getName()+"�߳�ִ����...");
			System.out.println(getName()+"�߳�ִ����...");
		}
	}
	public static void main(String[] args) {
		Demo1 demo1=new Demo1("first-thread");
		Demo1 demo2=new Demo1("second-thread");
		
		demo1.setDaemon(true);
		demo2.setDaemon(true);
		
		demo1.start();
		demo2.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
