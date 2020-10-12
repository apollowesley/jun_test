package com.lmy.thread.t2;

public class Demo3 {
	public static void main(String[] args) {
//		new Thread(){
//			@Override
//			public void run() {
//				System.out.println("thread start...");
//			}
//		}.start();
//		
//		new Thread(new Runnable(){
//			public void run() {
//				System.out.println("thread start...");
//			}
//		}).start();
		new Thread(new Runnable(){
			public void run() {
				System.out.println("runnable");
			}
		}){
			public void run() {
				System.out.println("sub");
			};
		}.start();
	}
}
