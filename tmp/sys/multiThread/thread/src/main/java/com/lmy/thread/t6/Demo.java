package com.lmy.thread.t6;

public class Demo {
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
		Demo d=new Demo();
		new Thread(new Runnable(){
			@Override
			public void run() {
				d.a();
			}
		}).start();
		new Thread(new Runnable(){
			@Override
			public void run() {
				d.b();
			}
		}).start();
	}
}
