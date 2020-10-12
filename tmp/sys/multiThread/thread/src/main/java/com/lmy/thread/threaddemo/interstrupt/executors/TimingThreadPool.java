package com.lmy.thread.threaddemo.interstrupt.executors;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Logger;

public class TimingThreadPool extends ThreadPoolExecutor{
	
	private final ThreadLocal<Long> startTime=new ThreadLocal<Long>();
	private final Logger log=Logger.getLogger("TimingThreadPool");
	private final AtomicLong numTasks=new AtomicLong();
	private final AtomicLong totalTime=new AtomicLong();
	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		System.out.println(Thread.currentThread().getName()+"TimingThreadPool.beforeExecute()");
		startTime.set(System.nanoTime());
	}
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		try {
			long endTime=System.nanoTime();
			long taskTime=endTime-startTime.get();
			numTasks.incrementAndGet();
			totalTime.addAndGet(taskTime);
			System.out.println(Thread.currentThread().getName()+"TimingThreadPool.afterExecute()");
		}finally{
			super.afterExecute(r, t);
		}
	}
	@Override
	protected void terminated() {
		try {
			System.out.println(Thread.currentThread().getName()+"TimingThreadPool.terminated()"+"terminated totalTime"+totalTime.get()
					+" numTasks:"+numTasks.get());
		}finally{
			super.terminated();
		}
	}

	public TimingThreadPool(int corePoolSize,
            int maximumPoolSize,
            long keepAliveTime,
            TimeUnit unit,
            BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	public static void main(String[] args) {
		TimingThreadPool exec=new TimingThreadPool(0, Integer.MAX_VALUE,
                60L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>());
		List<Callable<Integer>> list=new ArrayList<>();
		for(int i=0;i<30;i++){
			final int a=i;
			list.add(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					System.out.println(Thread.currentThread().getName());
					return a;
				}
			});
		}
		try {
			List<Future<Integer>> invokeAll = exec.invokeAll(list);
//			
			for(Future<Integer> future:invokeAll){
				System.out.println(future.get());
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		exec.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+"heelo");
			}
		});
		exec.shutdown();
	}
}
