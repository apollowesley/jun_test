package com.lmy.thread.ta8;

public class B implements Runnable{
	
	private Demo demo;
	public B(Demo demo){
		this.demo=demo;
	}
	@Override
	public void run() {
		while(true){
			demo.b();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
