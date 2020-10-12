package com.lmy.thread.t7;
public class Demo1 {
	public volatile int i=1;
	public static void main(String[] args) {
		Demo1 demo=new Demo1();
		new Thread(new Runnable(){
			@Override
			public void run() {
				demo.i=10;
			}
		}).start();
		new Thread(new Runnable(){
			@Override
			public void run() {
				System.out.println(demo.i);
			}
		}).start();
		System.out.println("×îÖÕ½á¹û£º"+demo.i);
	}
}
