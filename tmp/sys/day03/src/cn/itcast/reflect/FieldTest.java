package cn.itcast.reflect;

import java.lang.reflect.Field;


public class FieldTest {
	
	public static void main(String[] args) throws Exception {
//		HelloWorld h = new HelloWorld();
//		String s = h.name;
		// h.name = xxxx;
		//字节码
		Class clazz = Class.forName("cn.itcast.reflect.HelloWorld"); //HelloWorld
		//快捷方法  获得默认构造的实例
//		clazz.getConstructor().newInstance();
		Object obj = clazz.newInstance(); //obj -- h
		
		//获得私有字段private Integer age
		Field field = clazz.getDeclaredField("age");
		//强制设置访问权限
		field.setAccessible(true);
		//关联 obj.age
		Object value = field.get(obj);
		System.out.println(value);
		
		//设置值 obj.age = 67
		field.set(obj, 67);
		//再出输出
		value = field.get(obj);
		System.out.println(value);
		
	}
	
	public void demo() throws Exception {
		HelloWorld h = new HelloWorld();
//		String s = h.name;
		//字节码
		Class clazz = Class.forName("cn.itcast.reflect.HelloWorld"); //HelloWorld
		//快捷方法  获得默认构造的实例
//		clazz.getConstructor().newInstance();
		Object obj = clazz.newInstance(); //obj -- h
		
		//获得字段 String name =
		Field field = clazz.getField("name");  // name
		//关联 obj.name
		Object value = field.get(obj);
		System.out.println(value);
	}

}
