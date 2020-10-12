package com.lmy.thread.ta4;

public class Main {
	private int value;
	private MyLock lock=new MyLock();
	public int getNext(){
		lock.lock();
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i= value++;
		lock.unlock();
		return i;
	}
	
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
	
	public int getNext1(){
		try {
			Thread.sleep(300);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return value++;
	}
	public static void main(String[] args) {
		Main main=new Main();
		new Thread(new Runnable(){
			@Override
			public void run() {
//				while(true){
//					System.out.println(Thread.currentThread().getName()+":"+main.getNext());
//				}
				main.a();
			}
		}).start();
//		new Thread(new Runnable(){
//			@Override
//			public void run() {
//				while(true){
//					System.out.println(Thread.currentThread().getName()+":"+main.getNext());
//				}
//			}
//		}).start();
//		new Thread(new Runnable(){
//			@Override
//			public void run() {
//				while(true){
//					System.out.println(Thread.currentThread().getName()+":"+main.getNext());
//				}
//			}
//		}).start();
//		new Thread(new Runnable(){
//			@Override
//			public void run() {
//				while(true){
//					System.out.println(Thread.currentThread().getName()+":"+main.getNext());
//				}
//			}
//		}).start();
	}
}
