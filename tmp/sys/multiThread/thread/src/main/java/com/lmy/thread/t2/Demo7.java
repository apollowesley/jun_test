package com.lmy.thread.t2;

import java.util.Arrays;
import java.util.List;

public class Demo7 {
	
	public static void main(String[] args) {
		List<Integer> list=Arrays.asList(10,20,30,40);
		Demo7 demo7=new Demo7();
		int i=demo7.add(list);
		System.out.println(i);
	}
	
	public int add(List<Integer> values){
		return values.parallelStream().mapToInt((i)->{
			return i*2;
		}).sum();
	}
}
