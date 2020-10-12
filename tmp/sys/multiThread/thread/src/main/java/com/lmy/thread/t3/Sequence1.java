package com.lmy.thread.t3;

public class Sequence1 {
	private int i;
	public int getNext(){
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return i++;
	}
	
	public static void main(String[] args) {
		Sequence1 s=new Sequence1();
		new Thread(new Runnable(){
			@Override
			public void run() {
				while(true){
					System.out.println(Thread.currentThread().getName()+":"+s.getNext());
					try {
//						Thread.sleep(100);
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
//						Thread.sleep(100);
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
//						Thread.sleep(100);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}
