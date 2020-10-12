package com.lmy.thread.ta3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Demo {
	private Map<String,Object> map=new HashMap<>();
	private ReadWriteLock rwl=new ReentrantReadWriteLock();
	private Lock r=rwl.readLock();
	private Lock w=rwl.writeLock();
	
	public Object get(String key){
		r.lock();
		System.out.println(Thread.currentThread().getName()+"读 开始");
		try{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return map.get(key);
		}finally{
			r.unlock();
			System.out.println(Thread.currentThread().getName()+"读 结束");
		}
	}
	public void set(String key,Object obj){
		w.lock();
		System.out.println(Thread.currentThread().getName()+"写 开始");
		try{
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			map.put(key, obj);
		}finally{
			w.unlock();
			System.out.println(Thread.currentThread().getName()+"写 结束");
		}
	}
}
