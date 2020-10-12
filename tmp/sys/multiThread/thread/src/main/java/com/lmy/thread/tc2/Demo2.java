package com.lmy.thread.tc2;

public class Demo2 {
	private int a;
	private boolean flag;
	
	public void writer(){
		//这两个数据之间没有数据依赖性，因此处理器会对这两行代码进行指令重排序，然后就导致了 reader方法 会有问题。
		a=1;
		flag=true;
	}
	public void reader(){
		if(flag){
			int b=a+1;
			System.out.println(b);
		}
	}
}
