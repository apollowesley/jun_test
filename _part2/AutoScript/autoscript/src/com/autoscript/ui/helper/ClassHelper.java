/**
 * 
 */
package com.autoscript.ui.helper;

/**
 * 类工具类
 * 作者:龙色波
 * 日期:2013-10-16
 */
public class ClassHelper {
	/**
	 * 判断类是否实现className对应的接口
	 * @param c 被测试的类
	 * @param className 接口类名
	 * @return true--已实现,false--未实现
	 * @throws Exception
	 */
	public static boolean isImplInterface(Class<?> c,String className) throws Exception{
		if(c==null){
			throw new Exception(UIPropertyHelper.getString("exception.Class_is_empty"));
		}
		if(StringHelper.isEmpty(className)){
			throw new Exception(UIPropertyHelper.getString("exception.className_is_empty"));
		}
		Class<?> interfaces[] = c.getInterfaces();
		for(Class<?> ic:interfaces){
			if(ic.getName().equals(className)){
				return true;
			}else{
				if(isImplInterface(ic, className)){
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 根据类名实例华对象
	 * @param className
	 * @return
	 * @throws Exception
	 */
	public static Object instanceClass(String className) throws Exception{
		Class<?> c = Class.forName(className);
		return c.newInstance();
		
	}
}
