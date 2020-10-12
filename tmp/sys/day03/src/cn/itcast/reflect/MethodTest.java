package cn.itcast.reflect;

import java.lang.reflect.Method;



public class MethodTest {
	
	public static void main(String[] args) throws Exception {
		
//		HelloWorld h = new HelloWorld();
//		h.print();
		
		//获得class
		Class clazz = Class.forName("cn.itcast.reflect.HelloWorld");
		
		//实例化
		Object obj = clazz.newInstance();
		
		//获得方法 print(String str , int i)   //绕过自动装箱
		Method method = clazz.getDeclaredMethod("print", String.class,int.class);
		//强制设置访问权限
		method.setAccessible(true);
		
		//绑定,指定当前的方法，并将相应的实际参数传递
		method.invoke(obj, "rooot",250);
		
		
		
	}
	
	public void demo() throws Exception {
		

		//获得class
		Class clazz = Class.forName("cn.itcast.reflect.HelloWorld");
		
		//实例化
		Object obj = clazz.newInstance();
		
		//获得方法  print()
		Method method = clazz.getMethod("print", null);
		
		//绑定,指定当前的方法，并将相应的实际参数传递
		method.invoke(obj, null);
		
	}

}
