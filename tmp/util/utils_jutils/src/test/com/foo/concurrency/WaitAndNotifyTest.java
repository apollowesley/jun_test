package com.foo.concurrency;

import org.junit.Test;

/**
 * wait() notify() notifyAll()
 * 这3个方法是Object基类的native方法，要调用这些方法的线程必须持有对象的锁
 *
 * @author Dean
 */
public class WaitAndNotifyTest {


	private class Thread0 extends Thread {
		private final Object obj;

		Thread0(Object obj) {
			this.obj = obj;
		}

		@Override
		public void run() {
			synchronized (obj) {
				String threadName = Thread.currentThread().getName();
				System.out.println(threadName + "获取到了对象的锁");
				try {
					System.out.println(threadName + "开始wait()并释放对象的锁");
					obj.wait();
					System.out.println(threadName + "重新获取到对象的锁");//并不是醒了就能重新获取到锁，还得等其他线程释放对象锁
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class Thread1 extends Thread {
		private final Object obj;

		Thread1(Object obj) {
			this.obj = obj;
		}

		@Override
		public void run() {
			synchronized (obj) {
				String threadName = Thread.currentThread().getName();
				System.out.println(threadName + "获取到了对象的锁");
				System.out.println(threadName + "开始唤醒等待对象锁的其他线程");
				obj.notify();
//				obj.notifyAll(); //一般情况下调用notifyAll()更合理

				try {
					System.out.println(threadName + "开始sleep()一段时间并继续持有对象的锁");
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(threadName + "释放对象的锁");
			}
		}
	}


	@Test
	public void test() {
		Object obj = new Object();
		Thread0 thread0 = new Thread0(obj);
		Thread1 thread1 = new Thread1(obj);
		thread0.start();
		thread1.start();

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
