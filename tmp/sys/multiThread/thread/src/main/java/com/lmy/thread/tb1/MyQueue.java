package com.lmy.thread.tb1;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyQueue<E> {
	private Object[] obj;
	private int addIndex;
	private int removeIndex;
	private int queueSize;
	private Lock lock=new ReentrantLock();
	private Condition addCondition=lock.newCondition();
	private Condition removeCondition=lock.newCondition();
	public MyQueue(int count){
		obj=new Object[count];
	}
	public void add(E e){
		lock.lock();
		while(queueSize==obj.length){
			try {
				addCondition.await();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		obj[addIndex]=e;
		if(++addIndex==obj.length){
			addIndex=0;
		}
		queueSize++;
		removeCondition.signal();
		lock.unlock();
	}
	public void remove(){
		lock.lock();
		while(queueSize==0){
			try {
				System.out.println(Thread.currentThread().getName()+" 队列为空，不进行移除.");
				removeCondition.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		obj[removeIndex]=null;
		if(++removeIndex==obj.length){
			removeIndex=0;
		}
		queueSize--;
		addCondition.signal();
		lock.unlock();
	}
}
