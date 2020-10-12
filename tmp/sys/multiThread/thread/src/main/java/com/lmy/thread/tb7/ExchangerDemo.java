package com.lmy.thread.tb7;

import java.util.concurrent.Exchanger;

public class ExchangerDemo {
	public void a(Exchanger<String> exch){
		System.out.println("a方法开始执行");
		try {
			System.out.println("a方法开始抓取数据...");
			Thread.sleep(3000);
			System.out.println("a方法抓取数据结束...");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("a方法等待对比结果");
		String res="12345";
		try {
			exch.exchange(res);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void b(Exchanger<String> exch){
		System.out.println("b方法开始执行");
		try {
			System.out.println("b方法开始抓取数据...");
			Thread.sleep(3000);
			System.out.println("b方法抓取数据结束...");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String res="12345";
		try {
			String value=exch.exchange(res);
			System.out.println("b方法开始进行比对");
			System.out.println("比对结果是 "+res.equals(value));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		Exchanger<String> exch=new Exchanger<String>();
		ExchangerDemo d=new ExchangerDemo();
		new Thread(new Runnable() {
			@Override
			public void run() {
				d.a(exch);
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				d.b(exch);
			}
		}).start();
	}
}
