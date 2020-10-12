package com.lmy.thread.ta4;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
public class MyLock implements Lock{
	private Helper helper=new Helper();
	private class Helper extends AbstractQueuedSynchronizer{
		@Override
		protected boolean tryAcquire(int arg) {
			//如果第一个线程进来，可以拿到锁，因此我们可以返回true
			//如果第二个线程进来，则拿不到锁，返回false。如果当前进来的线程和当前保存的线程是同一个，则可以拿到锁，但是有代价，要更新状态值
			//如何判断是第一个线程进来还是其他线程进来。
			int currentState=getState();
			Thread currentThread=Thread.currentThread();
			if(currentState==0){
				if(compareAndSetState(0, arg)){
					setExclusiveOwnerThread(currentThread);
					return true;
				}
			}else if(getExclusiveOwnerThread()==currentThread){
				setState(currentState+1);
				return true;
			}
			return false;
		}
		@Override
		protected boolean tryRelease(int arg) {
			//锁的获取和释放肯定是一一对应的，那么调用此方法的线程一定是当前线程
			Thread t=Thread.currentThread();
			if(t!=getExclusiveOwnerThread()){
				throw new RuntimeException();
			}
			boolean flag=false;
			int state=getState()-arg;
			if(state==0){
				flag=true;
				setExclusiveOwnerThread(null);
			}
			setState(state);
			return flag;
		}
		private ConditionObject newCondition() {
			return new ConditionObject();
		}
	}
	@Override
	public void lock() {
		helper.acquire(1);
	}
	@Override
	public void lockInterruptibly() throws InterruptedException {
		helper.acquireInterruptibly(1);
	}
	@Override
	public boolean tryLock() {
		return helper.tryAcquire(1);
	}
	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		return helper.tryAcquireNanos(1, unit.toNanos(time));
	}
	@Override
	public void unlock() {
		helper.release(1);
	}
	@Override
	public Condition newCondition() {
		return helper.newCondition();
	}
}
