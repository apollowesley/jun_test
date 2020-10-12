package com.lmy.thread.ta9;

public class C implements Runnable{
	
	private Demo demo;
	public C(Demo demo){
		this.demo=demo;
	}
	@Override
	public void run() {
		while(true){
			demo.c();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
