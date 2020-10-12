package com.lmy.thread.threaddemo.p13_1_2;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {
	private Lock lock=new ReentrantLock();
	public static void main(String[] args) {
		Test test=new Test();
		MyThread thread1=new MyThread(test);
		MyThread thread2=new MyThread(test);
		thread1.start();
		thread2.start();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		thread2.interrupt();
		
	}
	public void insert(Thread thread) throws InterruptedException{
		lock.lockInterruptibly();
		try {
			System.out.println(thread.getName()+"得到了锁");
			long startTime=System.currentTimeMillis();
			for(;;){
				if(System.currentTimeMillis()-startTime>=10*1000){
					break;
					//插入数据
				}
			}
		} finally{
			System.out.println(Thread.currentThread().getName()+"执行finally");
			lock.unlock();
			System.out.println(Thread.currentThread().getName()+"释放了锁");
		}
	}
}
class MyThread extends Thread{
	private Test test=null;

	public MyThread(Test test) {
		this.test = test;
	}
	@Override
	public void run() {
		try {
			test.insert(Thread.currentThread());
		} catch (InterruptedException e) {
			System.out.println(Thread.currentThread().getName()+"被中断");
		}
	}
}
