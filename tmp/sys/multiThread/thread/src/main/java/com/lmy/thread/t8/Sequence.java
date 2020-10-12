package com.lmy.thread.t8;

import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReference;

public class Sequence {
	private int i;
	private int[] s={2,1,4,5};
	AtomicIntegerArray a=new AtomicIntegerArray(s);
	AtomicReference<User> age=new AtomicReference<>();
	AtomicIntegerFieldUpdater<User> old=AtomicIntegerFieldUpdater.newUpdater(User.class, "old");
	public int getNext(){
		User user=new User();
		old.getAndIncrement(user);
		System.out.println(user);
		return i++;
	}
	
	public static void main(String[] args) {
		Sequence s=new Sequence();
		new Thread(new Runnable(){
			@Override
			public void run() {
//				while(true){
					System.out.println(Thread.currentThread().getName()+":"+s.getNext());
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
//				}
			}
		}).start();
//		new Thread(new Runnable(){
//			@Override
//			public void run() {
//				while(true){
//					System.out.println(Thread.currentThread().getName()+":"+s.getNext());
//					try {
//						Thread.sleep(100);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();
//		new Thread(new Runnable(){
//			@Override
//			public void run() {
//				while(true){
//					System.out.println(Thread.currentThread().getName()+":"+s.getNext());
//					try {
//						Thread.sleep(100);
//					} catch (Exception e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();
	}
}
