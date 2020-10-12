package com.lmy.thread.t2;

public class StopThread extends Thread{
	
	private int i=0;
	private int j=0;
	
	@Override
	public void run() {
		synchronized (this) {
			++i;
			try {
				Thread.sleep(10000);
			} catch (Exception e) {
				e.printStackTrace();
			}
			++j;
		}
	}
	
	public void print(){
		System.out.println("i="+i+" j="+j);
	}
}
