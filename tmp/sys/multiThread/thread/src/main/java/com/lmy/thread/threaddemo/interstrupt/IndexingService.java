package com.lmy.thread.threaddemo.interstrupt;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class IndexingService {
	private static final File POISON = new File("");
	private final IndexerThread consumer=new IndexerThread();//消费者
	private final CrawlerThread producer=new CrawlerThread();//生产者
	private final BlockingQueue<File> queue=new LinkedBlockingDeque<File>();
	private final File root;
	public IndexingService(File root){
		this.root=root;
	}
	
	public void start(){
		producer.start();
		consumer.start();
	}
	public void stop(){
		producer.interrupt();//中断爬虫线程
	}
	public void awaitTermination() throws InterruptedException{
		consumer.join();
	}

	class CrawlerThread extends Thread {
		@Override
		public void run() {
			try {
				craw1(root);
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				while (true) {
					try {
						queue.put(POISON);
						break;
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}

		private void craw1(File root) throws InterruptedException {
			System.out.println("IndexingService.CrawlerThread.craw1()");
		}
	}

	class IndexerThread extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					File file = queue.take();
					if (file == POISON) {
						break;
					} else {
						indexFile(file);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		private void indexFile(File file) {
			System.out.println("IndexingService.IndexerThread.indexFile()");
		}
	}
	public static void main(String[] args) {
		
	}
}
