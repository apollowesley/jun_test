package com.lmy.thread.t6;

public class Demo1 {
	public synchronized void a(){
		System.out.println("Demo.a()");
//		b();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public synchronized void b(){
		System.out.println("Demo.b()");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		Demo1 d1=new Demo1();
		Demo1 d2=new Demo1();
		new Thread(new Runnable(){
			@Override
			public void run() {
				d1.a();
			}
		}).start();
		new Thread(new Runnable(){
			@Override
			public void run() {
				d2.b();
			}
		}).start();
	}
}
