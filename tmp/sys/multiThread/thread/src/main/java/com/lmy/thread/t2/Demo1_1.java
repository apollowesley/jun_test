package com.lmy.thread.t2;

public class Demo1_1 extends Thread{
	
	public Demo1_1(String name){
		super(name);
	}
	
	@Override
	public void run() {
		while(!interrupted()){
			System.out.println(getName()+"线程执行中...");
			try {
				sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		Demo1_1 demo1=new Demo1_1("first-thread");
		Demo1_1 demo2=new Demo1_1("second-thread");
		
		demo1.start();
		demo2.start();

		demo1.interrupt();
		
	}
}
