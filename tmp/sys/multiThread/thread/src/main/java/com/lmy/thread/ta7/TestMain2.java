package com.lmy.thread.ta7;

public class TestMain2 {
	public static void main(String[] args) {
		Tmall2 tmall=new Tmall2();
		PushTarget2 pt=new PushTarget2(tmall);
		TakeTarget2 tt=new TakeTarget2(tmall);
		
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
