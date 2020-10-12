package com.lmy.thread.td1;

import java.util.concurrent.locks.StampedLock;

public class Demo {
	private int balance;
	private StampedLock lock=new StampedLock();
	public void conditionReadWrite(int value){
		//首先判断balance 的值是否符合更新的条件
		long stamp=lock.readLock();
		while(balance>0){
			long writeStamp=lock.tryConvertToWriteLock(stamp);
			//成功转换为写锁
			if(writeStamp!=0){
				stamp=writeStamp;
				balance+=value;
				break;
			}else{
				//没有转换成写锁，这里需要首先释放读锁，然后再拿到写锁
				lock.unlockWrite(writeStamp);
				//获取写锁
				stamp=lock.writeLock();
			}
		}
		lock.unlock(stamp);
	}
	public void read(){
		long stamp=lock.readLock();
		lock.tryOptimisticRead();
		int c=balance;
		//...
		lock.unlockRead(stamp);
	}
	public void optimisticRead(){
		long stamp=lock.tryOptimisticRead();
		int c=balance;
		//这里可能会出现了写操作，因此要进行判断
		if(!lock.validate(stamp)){
			//要从新读取
			long readStamp=lock.readLock();
			c=balance;
			stamp=readStamp;
		}
		//...
		lock.unlockRead(stamp);
	}
	public void write(int value){
		long stamp=lock.writeLock();
		balance+=value;
		lock.unlockWrite(stamp);
	}
}
