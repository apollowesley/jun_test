package com.xieke.java8.demo.abstractfactory;

/**
 * 发送短信工厂实现类
 * 
 * @author 邪客
 *
 */
public class SendSmsFactory implements SendFactory{  
  
    @Override  
	public Sender build() {
        return new SmsSender();  
    }  

}