package com.lmy.thread.ta3;

public class Main {

	public static void main(String[] args) {
		Demo d = new Demo();
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				d.set("a", 10);
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + ":"+ d.get("a"));
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName() + ":"+ d.get("a"));
			}
		}).start();

//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println(Thread.currentThread().getName() + ":"+ d.get("a"));
//			}
//		}).start();
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				System.out.println(Thread.currentThread().getName() + ":"+ d.get("a"));
//			}
//		}).start();
	}

}
