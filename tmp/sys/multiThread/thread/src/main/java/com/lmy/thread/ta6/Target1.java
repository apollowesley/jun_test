package com.lmy.thread.ta6;

public class Target1 implements Runnable{
	private Demo3 demo=new Demo3();
	
	public Target1(Demo3 demo){
		this.demo=demo;
	}
	@Override
	public void run() {
		demo.set();
	}

}
