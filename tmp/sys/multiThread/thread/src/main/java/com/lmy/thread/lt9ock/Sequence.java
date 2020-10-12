package com.lmy.thread.lt9ock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Sequence {
	private int i;
	Lock lock=new ReentrantLock();
	public int getNext(){
		lock.lock();
		i++;
		lock.unlock();
		return i;
//		return i++;
	}
	
	public static void main(String[] args) {
		Sequence s=new Sequence();
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					System.out.println(Thread.currentThread().getName()+":"+s.getNext());
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					System.out.println(Thread.currentThread().getName()+":"+s.getNext());
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					System.out.println(Thread.currentThread().getName()+":"+s.getNext());
					try {
						Thread.sleep(100);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
