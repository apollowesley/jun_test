package com.lmy.thread.ta1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class MyLock implements Lock{
	private boolean isLocked = false;
//	private AtomicBoolean isLocked1=new AtomicBoolean(false);
	Thread lockBy=null;
	private int lockCount;

	@Override
	public synchronized void lock() {
		Thread currentThread=Thread.currentThread();
		while(isLocked&&lockBy!=currentThread){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		isLocked=true;
		lockBy=currentThread;
		lockCount++;
	}
	
	@Override
	public synchronized void unlock() {
		Thread currentThread=Thread.currentThread();
		if(currentThread==lockBy){
			lockCount--;
			if(lockCount==0){
				notify();
				isLocked=false;
			}
		}
		
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {
		
	}

	@Override
	public boolean tryLock() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Condition newCondition() {
		// TODO Auto-generated method stub
		return null;
	}

}
