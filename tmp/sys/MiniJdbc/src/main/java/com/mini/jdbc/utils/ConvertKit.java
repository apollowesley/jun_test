package com.mini.jdbc.utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.mini.jdbc.Record;

/**
 * 
 * @author sxjun1904
 * @create 2017-5-23
 */
public class ConvertKit {
	
	
	/**
	 * 反射生成对象
	 * @param record
	 * @param t
	 * @return
	 */
	public static <T> List<T> toEntityList(List<Record> records,Class<T> clazz){
		List<T> l = new ArrayList();
		for(Record record: records){
			l.add(toEntity(record, clazz, false));
		}
		return l;
	}
	
	/**
	 * 反射生成对象
	 * @param record
	 * @param t
	 * @return
	 */
	public static <T> List<T> toEntityList(List<Record> records,Class<T> clazz, boolean replace){
		List<T> l = new ArrayList();
		for(Record record: records){
			l.add(toEntity(record, clazz, replace));
		}
		return l;
	}
	
	public static <T> T toEntity(Record record,Class<T> clazz){
		return toEntity(record,clazz,false);
	}
	
	/**
	 * 反射生成对象
	 * @param record
	 * @param t
	 * @return
	 */
	public static <T> T toEntity(Record record,Class<T> clazz, boolean replace){
		T o = null;
		try {
			o = (T) clazz.newInstance();
			for (Entry<String, Object> entry : record.entrySet()) {
				Method method = getSetMethod(o.getClass(), entry.getKey(),replace);       
			    try {   
			    	if(method!=null){
			    		Class[] types= getMethodParamTypes(o.getClass(), entry.getKey(),replace);
			    		Object val = entry.getValue();
			    		if(types.length>0 && types[0] == boolean.class)
			    			val = entry.getValue()!=null&&"1".equals(entry.getValue())?true:false;
			    		method.invoke(o, new Object[] { val });    
			    	}
			    } catch (Exception e) {     
			        e.printStackTrace();       
			    }
			}
		} catch (InstantiationException |IllegalAccessException e) {
			e.printStackTrace();
		}
		return o;
	}
	
	/**
	 * 获取set方法
	 * @param objectClass
	 * @param fieldName
	 * @return
	 */
	public static Method getSetMethod(Class objectClass, String fieldName, boolean replace) {       
	    try {   
	    	String lm = getSetMethod(fieldName, replace);
	        Method lmethod = null;
	        Method[] methods = objectClass.getMethods();
	        for(Method method :methods){
	        	if(method.getName().equals(lm)){
	        		lmethod = method;
	        		break;
	        	}
	        }
	        return lmethod;       
	    } catch (Exception e) {       
	        e.printStackTrace();       
	    }       
	    return null;       
	}
	
	/**
	 * 获取set方法名称
	 * @param fieldName
	 * @param replace
	 * @return
	 */
	public static String getSetMethod(String fieldName, boolean replace){
		String lm = "set";
    	if(replace){
	        String m = StrKit.firstCharToUpperCase(fieldName);  
	        String[] ms = m.split("_");
	        lm += ms[0];
	        if(ms.length>1)
		        for(int i=1;i<ms.length;i++){
		        	lm += StrKit.firstCharToUpperCase(ms[i]);
		        }
    	}else
    		lm += StrKit.firstCharToUpperCase(fieldName);
    	return lm;
	}
	
	/**
	 * 根据方法名称取得反射方法的参数类型(没有考虑同名重载方法使用时注意)
	 * @param objectClass 类
	 * @param methodName  方法名
	 * @return
	 * @throws ClassNotFoundException
	 */
	public static Class[]  getMethodParamTypes(Class objectClass, String fieldName, boolean replace) {
		Class[] params = null;
		String lm = getSetMethod(fieldName, replace);
		Method[]  methods = objectClass.getMethods();//全部方法
		for (int  i = 0;  i< methods.length; i++) {
	        if(lm.equals(methods[i].getName())){//和传入方法名匹配 
		        params = methods[i].getParameterTypes();
	        }
		}
		return params;
	}
}
