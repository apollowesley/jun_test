package com.lmy.thread.threaddemo;

import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo {
	public static void main(String[] args) throws Exception {
		Timer timer=new Timer();
		timer.schedule(new ThrowTask(), 1000);
		Thread.sleep(1000);
		timer.schedule(new ThrowTask(), 1000);
		Thread.sleep(6000);
//		SECONDS.sleep(1);
	}
	static class ThrowTask extends TimerTask{

		@Override
		public void run() {
			throw new RuntimeException();
		}
		
	}
}
