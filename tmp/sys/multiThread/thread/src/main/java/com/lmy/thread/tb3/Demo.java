package com.lmy.thread.tb3;

public class Demo {
	private ThreadLocal<Integer> count=new ThreadLocal<Integer>(){
		protected Integer initialValue() {
			return new Integer(0);
		}
	};
	public int getNext(){
		Integer value=count.get();
		value++;
		count.set(value);
		return value;
	}
	public static void main(String[] args) {
		Demo demo=new Demo();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					System.out.println(Thread.currentThread().getName()+" "+demo.getNext());
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while(true){
					System.out.println(Thread.currentThread().getName()+" "+demo.getNext());
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}
}