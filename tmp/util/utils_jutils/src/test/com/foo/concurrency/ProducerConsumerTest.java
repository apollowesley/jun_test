package com.foo.concurrency;

import org.junit.Test;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 生产者消费者问题
 *
 * @author Dean
 */
public class ProducerConsumerTest {

	/**
	 * 生产者
	 */
	private class Producer implements Runnable {
		private final Storage storage;

		Producer(Storage storage) {
			this.storage = storage;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (storage) {
					while (storage.isFull()) {
						System.out.println("仓库满了");
						try {
							storage.wait(); //线程必须持有对象锁才能调用wait()、notify()、notifyAll()
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					produce();
				}

				synchronized (storage) {
					storage.notifyAll();
				}

				try {
					Thread.sleep(1000); //sleep()是为了不让生产者一直不停生产到仓库满，让其他线程抢到CPU
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

			}
		}

		/**
		 * 生产
		 */
		private void produce() {
			Product product = new Product();
			System.out.println("生产者" + Thread.currentThread().getName() + "生产了一个产品");
			storage.put(product);
		}
	}

	/**
	 * 消费者
	 */
	private class Consumer implements Runnable {
		private final Storage storage;

		Consumer(Storage storage) {
			this.storage = storage;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (storage) {
					while (storage.isEmpty()) {
						System.out.println("仓库没货了，消费者" + Thread.currentThread().getName() + "未能消费产品");
						try {
							storage.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					consume();
				}

				synchronized (storage) {
					storage.notifyAll();
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		/**
		 * 消费
		 */
		private void consume() {
			storage.get();
			System.out.println("消费者" + Thread.currentThread().getName() + "消费了一个产品");
		}
	}

	/**
	 * 仓库
	 */
	private class Storage {
		private LinkedList<Product> productList = new LinkedList<>();
		private int max = 10; //仓库最大容量

		/**
		 * 往仓库放
		 */
		synchronized void put(Product product) {
			if (productList.size() < max) {
				productList.addLast(product);
			}
		}

		/**
		 * 从仓库取
		 */
		synchronized Product get() {
			if (productList.size() > 0) {
				return productList.removeFirst();
			}
			return null;
		}

		boolean isFull() {
			return productList.size() >= max;
		}

		boolean isEmpty() {
			return productList.size() <= 0;
		}
	}

	/**
	 * 生产的产品
	 */
	private class Product {
	}

	@Test
	public void test() {
		Storage storage = new Storage();

		//一个或者多个生产者和消费者
		Producer producer = new Producer(storage);
		Consumer consumer = new Consumer(storage);
		Consumer consumer2 = new Consumer(storage);

		ExecutorService executorService = Executors.newCachedThreadPool();
		executorService.execute(producer);
		executorService.execute(consumer);
		executorService.execute(consumer2);

		try {
			Thread.sleep(100000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		executorService.shutdown();
	}
}
