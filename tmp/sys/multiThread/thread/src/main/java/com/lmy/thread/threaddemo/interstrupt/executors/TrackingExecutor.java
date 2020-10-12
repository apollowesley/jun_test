package com.lmy.thread.threaddemo.interstrupt.executors;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class TrackingExecutor extends AbstractExecutorService{
	private final ExecutorService exec;
	private final Set<Runnable> tasksCancelledAtShutdown=Collections.synchronizedSet(new HashSet<Runnable>());
	
	public TrackingExecutor(ExecutorService exec) {
		this.exec = exec;
	}
	@Override
	public void shutdown() {
		exec.shutdown();
	}
	@Override
	public List<Runnable> shutdownNow() {
		// TODO Auto-generated method stub
		return exec.shutdownNow();
	}
	@Override
	public boolean isShutdown() {
		// TODO Auto-generated method stub
		return exec.isShutdown();
	}
	@Override
	public boolean isTerminated() {
		// TODO Auto-generated method stub
		return exec.isTerminated();
	}
	@Override
	public boolean awaitTermination(long timeout, TimeUnit unit)
			throws InterruptedException {
		// TODO Auto-generated method stub
		return exec.awaitTermination(timeout, unit);
	}
	@Override
	public void execute(Runnable runnable) {
		exec.execute(new Runnable() {
			@Override
			public void run() {
				try {
					runnable.run();
				}finally{
					if(isShutdown()&&Thread.currentThread().isInterrupted()){
						tasksCancelledAtShutdown.add(runnable);
					}
				}
			}
		});
	}
	public List<Runnable> getCancelledTasks(){
		if(!exec.isShutdown()){
			throw new IllegalStateException();
		}
		return new ArrayList<Runnable>(tasksCancelledAtShutdown);
	}
	
	
}
