package cn.itcast.reflect;

import java.lang.reflect.Constructor;

public class ConstructorTest {
	
	public static void main(String[] args) throws Exception {
		
//		HelloWorld hello = new HelloWorld();
		
		//获得字节码对象在内存中的表示
		Class clazz = Class.forName("cn.itcast.reflect.HelloWorld"); //HelloWorld
		//获得私有的构造，HelloWorld(String str,Integer i)  形参
		Constructor cons = clazz.getDeclaredConstructor(String.class,Integer.class);
		
		//java.lang.IllegalAccessException
		//System.out.println(cons.isAccessible());
		//强制设置访问权限
		cons.setAccessible(true);
		
		//实例化 new  实参
		Object obj = cons.newInstance("admin",12334);
		
	}
	
	//获得共有的构造方法，并执行
	public void demo() throws Exception {
		
//		HelloWorld hello = new HelloWorld();
		
		//获得字节码对象在内存中的表示
		Class clazz = Class.forName("cn.itcast.reflect.HelloWorld"); //HelloWorld
		//获得默认构造 HelloWorld()
		Constructor cons = clazz.getConstructor();  //HelloWorld()
		//获得实例
		Object obj = cons.newInstance(); // new HelloWorld();
		System.out.println(obj);
	}

}
