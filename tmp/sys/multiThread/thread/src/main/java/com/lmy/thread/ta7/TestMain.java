package com.lmy.thread.ta7;

public class TestMain {
	public static void main(String[] args) {
		Tmall tmall=new Tmall();
		PushTarget pt=new PushTarget(tmall);
		TakeTarget tt=new TakeTarget(tmall);
		
		new Thread(pt).start();
		new Thread(pt).start();
		new Thread(pt).start();
		new Thread(pt).start();
		new Thread(pt).start();
		new Thread(tt).start();
		new Thread(tt).start();
		new Thread(tt).start();
		new Thread(tt).start();
		new Thread(tt).start();
		new Thread(tt).start();
		new Thread(tt).start();
		new Thread(tt).start();
		
	}
}
