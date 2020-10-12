package com.siweifu.utils;

import com.siweifu.utils.ThreadUtils.LazyAction;

/**
 * 线程测试
 * @title ThreadTest.java
 * @description 
 * @company 北京思维夫网络科技有限公司
 * @author 卢春梦
 * @version 1.0
 * @created 2015年6月6日下午12:59:26
 */
public class ThreadTest {

	public static void main(String[] args) {
		// 延迟运行
		ThreadUtils.lazyRun(1000, new LazyAction() {
			@Override
			public void exec() {
				System.out.println(Thread.currentThread().getName());
				System.out.println("123123");
			}
		});
		System.out.println(Thread.currentThread().getName());
	}
}
