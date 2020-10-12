package com.lmy.thread.ta9;

public class A implements Runnable{
	
	private Demo demo;
	public A(Demo demo){
		this.demo=demo;
	}
	@Override
	public void run() {
		while(true){
			demo.a();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
