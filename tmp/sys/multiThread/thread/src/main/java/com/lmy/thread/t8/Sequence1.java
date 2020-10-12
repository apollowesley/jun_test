package com.lmy.thread.t8;

import java.util.concurrent.atomic.AtomicInteger;

public class Sequence1 {
	private AtomicInteger aaa=new AtomicInteger();
	public int getNext(){
		return aaa.getAndIncrement();
	}
	
	public static void main(String[] args) {
		Sequence1 s=new Sequence1();
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
