package com.lmy.thread.tb4;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
	private int[] nums;
	public CountDownLatchDemo(int line) {
		nums=new int[line];
	}
	
	public void calculate(String line,int index,CountDownLatch latch){
		String[] numbers=line.split(",");
		int total=0;
		for(int i=0;i<numbers.length;i++){
			total+=Integer.parseInt(numbers[i]);
		}
		nums[index]=total;
		System.out.println("计算数据 "+line+" ，得到这行的总和是 "+total);
		latch.countDown();
		System.out.println(Thread.currentThread().getName()+"计算完毕");
	}
	public void sum(){
		int total=0;
		for(int i=0;i<nums.length;i++){
			total+=nums[i];
		}
		System.out.println("数据总和是 "+total);
	}
	
	public static void main(String[] args) throws Exception{
		String line="";
		List<String> result=new ArrayList<>();
		BufferedReader br=null;
		try {
			br=new BufferedReader(new FileReader("C:/filesystem/sum.txt"));
			while((line=br.readLine())!=null){
				result.add(line);
			}
			CountDownLatch latch=new CountDownLatch(result.size());
			CountDownLatchDemo d=new CountDownLatchDemo(result.size());
			for(int i=0;i<result.size();i++){
				final int j=i;
				new Thread(new Runnable() {
					@Override
					public void run() {
						d.calculate(result.get(j), j,latch);
					}
				}).start();
			}
			latch.await();
			d.sum();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			if(br!=null){
				br.close();
			}
		}
	}
	
}
