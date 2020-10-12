package com.lmy.thread.t2;

public class StopDemo {
	
	public static void main(String[] args) {
		StopThread thread=new StopThread();
		thread.start();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thread.stop();
		while (thread.isAlive()) {
			
		}
		thread.print();
	}

}
