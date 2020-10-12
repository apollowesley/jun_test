package cn.itcast.reflect;

import java.util.ArrayList;

public class ClassTest {
	
	public static void main(String[] args) throws Exception{
		
		//获得字节码  cn.itcast.reflect.HelloWorld
		Class clazz = Class.forName("cn.itcast.reflect.HelloWorld");
		System.out.println(clazz);
		
		//已知类
		Class clazz2 = String.class;
		System.out.println(clazz2);
		
		//已知对象
		Object obj = new ArrayList();
		Class clazz3 = obj.getClass();
		System.out.println(clazz3);
		
	}

}
