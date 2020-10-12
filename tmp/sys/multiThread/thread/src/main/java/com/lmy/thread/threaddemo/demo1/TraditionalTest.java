package com.lmy.thread.threaddemo.demo1;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class TraditionalTest {
	public static void main(String[] args) {
		int taskSize=5;
		ExecutorService executor=Executors.newFixedThreadPool(taskSize);
		List<Future<Integer>> futureList=new ArrayList<>();
		for(int i=0;i<taskSize;i++){
			int sleep=taskSize-i;//睡眠时间
			int value=i;//返回结果
			Future<Integer> future=executor.submit(new ReturnAfterSleepCallable(sleep, value));
			futureList.add(future);
		}
		while(taskSize>0){
			for(Future<Integer> future:futureList){
				Integer result=null;
				try {
					result=future.get(0,TimeUnit.SECONDS);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(result!=null){
					System.out.println("result="+result);
					futureList.remove(future);
					taskSize--;
					break;
				}
			}
		}
		System.out.println("all over");
		executor.shutdown();
	}
}
