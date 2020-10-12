package com.lmy.thread.ta6;

public class Demo2 {
	private volatile int singal;
	public void set(int value){
		this.singal=value;
	}
	public int get(){
		return this.singal;
	}
	public static void main(String[] args) {
		Demo2 d=new Demo2();
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (d) {
					System.out.println("修改状态的线程执行");
					try {
						Thread.sleep(5000);
					} catch (Exception e) {
						// TODO: handle exception
					}
					d.set(1);
					System.out.println("状态值修改成功...");
					d.notify();
				}
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				synchronized (d) {
					System.out.println("获取状态值开始");
					while(d.get()!=1){
						try {
							d.wait();
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					System.out.println("获取状态值成功");
				}
			}
		}).start();
	}

}
