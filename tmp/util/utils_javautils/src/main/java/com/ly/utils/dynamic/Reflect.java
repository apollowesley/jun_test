package com.ly.utils.dynamic;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 用反射获取方法
 * 
 * @Version 1.4.3
 */
public class Reflect {
	/**
	 * 获取类的方法(包括重载方法)
	 * @param clazz			类
	 * @param methodName	方法名
	 * @param args			参数(参数类型需要和方法参数类型相同，Object随意)
	 * @return				获取的方法
	 */
	public static Method getOverloadMethod(Class<?> clazz, String methodName, Object... args){
		Method[] methods = clazz.getMethods();
		for (Method method : methods) {
			if (methodName.equals(method.getName())) {
				if(args.length == 0){
					return method;
				}
				Class<?>[] params = method.getParameterTypes();
				if (args.length == params.length) {
					for (int j = 0; j < args.length; j++) {
						if (params[j] instanceof Object || params[j].equals(args[j].getClass())) {
							if (j == args.length - 1) {
								return method;
							}
						} 
					}
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取类的方法(不包括重载方法)
	 * @param clazz			类
	 * @param methodName	方法名
	 * @param args			参数
	 * @return				获取的方法
	 */
	public static Method getMethod(Class<?> clazz, String methodName){
		return getOverloadMethod(clazz,methodName);
	}
	
	/**
	 * 根据方法名获取类的set方法
	 * @param clazz			类
	 * @param methodName	方法名(不加“set”)
	 * @return				set方法
	 */
	public static Method getSetMethod(Class<?> clazz, String methodName) {
		return getMethod(clazz, firstUpperCase("set",methodName));
	}

	/**
	 * 根据方法名获取类的get方法
	 * @param clazz			类
	 * @param methodName	方法名(不加“get”)
	 * @return				get方法
	 */
	public static Method getGetMethod(Class<?> clazz, String methodName) {
		return getMethod(clazz, firstUpperCase("get",methodName));
	}

	/**
	 * 通过方法名获取对象所有set方法(不加“set”)
	 * 
	 * @param methodNames	String[] attribute
	 * @return Map<String,Method> String:attribute,Method:getMethodName
	 */
	public static Map<String,Method> getSetMethods(Class<?> clazz, String...methodNames){
		Map<String,Method> map = new HashMap<String, Method>();
		for (String methodName : methodNames) {			
			map.put(methodName , getSetMethod(clazz, methodName));
		}
		return map;
	}
	
	/**
	 * 通过方法名获取对象所有get方法(不加“get”)
	 * 
	 * @param methodNames	String[] methodNames
	 * @return Map<String,Method> String:methodName,Method:getMethodName
	 */
	public static Map<String,Method> getGetMethods(Class<?> clazz, String...methodNames){
		Map<String,Method> map = new HashMap<String, Method>();
		for (String methodName : methodNames) {			
			map.put(methodName , getGetMethod(clazz, methodName));
		}
		return map;
	}
	

	/**
	 * 通过属性名获取对象所有get方法
	 * 
	 * @param clazz
	 * @return	Map<String,Method>  String是类的属性名
	 */
	public static Map<String,Method> getGetMethods(Class<?> clazz){
		return getGetMethods(clazz,FieldsToStrings(clazz.getDeclaredFields()));
	}
	
	/**
	 * 通过属性名获取对象所有set方法
	 * 
	 * @param clazz
	 * @return	Map<String,Method>  String是类的属性名
	 */
	public static Map<String,Method> getSetMethods(Class<?> clazz){
		return getSetMethods(clazz,FieldsToStrings(clazz.getDeclaredFields()));
	}
	
	/**
	 * 将所有属性转换为字符串数组
	 * 
	 * @param fields
	 * @return
	 */
	public static String[] FieldsToStrings(Field[] fields){
		String[] str = new String[fields.length];
		for (int i=0;i<fields.length;i++) {
			str[i] = fields[i].getName();
		}
		return str;
	}
	
	/**
	 * 执行参数为String的set方法
	 * 自动转换匹配的类型
	 * 
	 * @param method	set的方法名
	 * @param obj		对象
	 * @param value		String类型的参数
	 * @param dateFormat	时间格式
	 */
	public static void invokeSetValue(Method method, Object obj,
			String value, String dateFormat) throws Exception {
		if(value==null){
			return;
		}
		Class<?> type = method.getParameterTypes()[0];
		/**
		 * 如果是(整型,浮点型,布尔型,字节型,时间类型), 按照各自的规则把value值转换成各自的类型
		 * 否则一律按类型强制转换(比如:String类型)
		 */
		if (type == int.class || type == java.lang.Integer.class) {
			int index = value.lastIndexOf(".");
			if(index != -1){
				value = value.substring(0, index);
			}
			method.invoke(obj, Integer.valueOf(value));
		} else if (type == float.class || type == java.lang.Float.class) {
			method.invoke(obj, Float.valueOf(value));
		} else if (type == double.class || type == java.lang.Double.class) {
			method.invoke(obj, Double.valueOf(value));
		} else if (type == byte.class || type == java.lang.Byte.class) {
			method.invoke(obj, Byte.valueOf(value));
		} else if (type == boolean.class || type == java.lang.Boolean.class) {
			method.invoke(obj, Boolean.valueOf(value));
		} else if (type == java.util.Date.class) {
			method.invoke(obj, new SimpleDateFormat(dateFormat).parse(value));
		} else if (type == long.class || type == java.lang.Long.class) {
			method.invoke(obj, Long.parseLong(value));
		} else {
			// 按类型强制转换
			method.invoke(obj, type.cast(value));
		}
	}

	/**
	 * 执行对象的方法
	 * 
	 * @param obj			对象
	 * @param methodName	方法名
	 * @param args			参数
	 * @return				返回值
	 */
	public static Object invoke(Object obj, String methodName,Object ... args) {
		Method method = getOverloadMethod(obj.getClass(), methodName, args);
		try {
			return method.invoke(obj, args);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 执行对象的get方法
	 * 
	 * @param obj			对象
	 * @param methodName	方法名(不加“get”)
	 * @param args			参数
	 * @return				返回值
	 */
	public static Object invokeGetMethod(Object obj, String getName){
		return invoke(obj, firstUpperCase("get", getName));
	}
	
	/**
	 * 执行对象的set方法
	 * 
	 * @param obj			对象
	 * @param methodName	方法名(不加“set”)
	 * @param args			参数
	 * @return				返回值
	 */
	public static void invokeSetMethod(Object obj, String getName,Object args){
		invoke( obj, firstUpperCase("set", getName), args);
	}
	
	/**
	 * 将字符串首字母转大写字母
	 * 
	 * @param before	转换后在之前添加的字符串
	 * @param want		要转换的字符串
	 * @return
	 */
	public static String firstUpperCase(String before,String want) {
		return before + want.substring(0, 1).toUpperCase() + want.substring(1);
	}
}