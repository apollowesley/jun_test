package com.lmy.thread.ta1;

public class Demo {
	private MyLock lock=new MyLock();
	public void a(){
		lock.lock();
		System.out.println("a");
		b();
		lock.unlock();
	}
	public void b(){
		lock.lock();
		System.out.println("b");
		lock.unlock();
	}
	public static void main(String[] args) {
		Demo demo=new Demo();
		new Thread(new Runnable(){
			@Override
			public void run() {
				demo.a();
			}
		}).start();
		new Thread(new Runnable(){
			@Override
			public void run() {
				demo.b();
			}
		}).start();
	}
}
