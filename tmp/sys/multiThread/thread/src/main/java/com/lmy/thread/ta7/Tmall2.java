package com.lmy.thread.ta7;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Tmall2 {
	private int count;
	public final int MAX_COUNT=10;
	private Lock lock=new ReentrantLock();
	private Condition a=lock.newCondition();
	private Condition b=lock.newCondition();
	
	public void push(){
		lock.lock();
		while(count>=MAX_COUNT){
			try {
				System.out.println(Thread.currentThread().getName()+" ��������ﵽ���ޣ�������ֹͣ������");
				a.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		count++;
		System.out.println(Thread.currentThread().getName()+" ��������������ǰ���Ϊ��"+count);
		b.signal();
		lock.unlock();
	}
	public void take(){
		lock.lock();
		while(count<=0){
			try {
				System.out.println(Thread.currentThread().getName()+" �������Ϊ�㣬�����ߵȴ���");
				b.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		count--;
		System.out.println(Thread.currentThread().getName()+" ���������ѣ���ǰ���Ϊ��"+count);
		a.signal();
		lock.unlock();
	}
}
