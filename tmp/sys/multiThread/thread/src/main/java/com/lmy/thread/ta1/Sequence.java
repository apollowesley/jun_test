package com.lmy.thread.ta1;

public class Sequence {
	private int value;
	private MyLock lock=new MyLock();
	public int getNext(){
		lock.lock();
		value++;
		lock.unlock();
		return value;
	}
	public static void main(String[] args) {
		Sequence s=new Sequence();
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					System.out.println(s.getNext());
					try {
//						Thread.sleep(1000);
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
					System.out.println(s.getNext());
					try {
//						Thread.sleep(1000);
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
					System.out.println(s.getNext());
					try {
//						Thread.sleep(1000);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
