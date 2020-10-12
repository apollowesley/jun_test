package com.xieke.java8.demo.abstractfactory;

/**
 * 发送工厂接口
 * 
 * @author 邪客
 *
 */
public interface SendFactory {

	/**
	 * 构建发送对象
	 * 
	 * @return
	 */
	Sender build();

}