package com.abc.demo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import com.abc.bean.Preson;
import com.abc.bean.SuperMan;

public class app {
	public static void main(String[] args) throws Exception {
		
			Class<?> p = Class.forName("com.abc.bean.Preson");
			Preson p1 = (Preson) p.newInstance();
			Constructor<?> constructor = p.getConstructor(String.class,int.class);
			Preson p2 = (Preson) constructor.newInstance("张无忌",299);
			p1.setName("张三丰");
			System.out.println(p1.getName()+","+p2.getName());
			p1.eat();
			p2.eat();
			
			Class<SuperMan> s = SuperMan.class;
			SuperMan superMan = s.newInstance();
			superMan.fly();
			superMan.eat();
			superMan.walk(1000);
			//获取继承的父类
			System.out.println(s.getSuperclass());
			//获取本类实现的接口
			Class<?>[] interfaces = s.getInterfaces();
			System.out.println(interfaces[0]);
			//获取反射类的包
			System.out.println(s.getPackage());
			
			//运行反射类方法
			Method method = s.getMethod("walk",int.class);
			method.invoke(superMan,100);
			
			
			
		
	}
}
