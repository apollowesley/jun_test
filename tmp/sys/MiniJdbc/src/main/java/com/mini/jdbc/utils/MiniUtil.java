package com.mini.jdbc.utils;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import com.mini.example.bean.User;
import com.mini.jdbc.BaseEntity;
import com.mini.jdbc.DatabaseRouter;
import com.mini.jdbc.Record;
import com.mini.jdbc.StrongEntity;
import com.mini.jdbc.WeakEntity;
import com.mini.jdbc.utils.EnumClazz.Caps;

public class MiniUtil {
	public static void main(String[] args) {
		isBaseEntity(User.class);
	}
	
	/**
	 * 是否为基本类型或基本类型对应的类
	 * @param clazz
	 * @return
	 */
	public static boolean isPrimitiveClass(Class clazz){
		return clazz.isPrimitive()
		||String.class.equals(clazz)
		||Integer.class.equals(clazz)
		||Double.class.equals(clazz)
		||Float.class.equals(clazz)
		||Long.class.equals(clazz)
		||Short.class.equals(clazz)
		||Boolean.class.equals(clazz)
		||Byte.class.equals(clazz)
		||char.class.equals(clazz);
	}
	
	/**
	 * 查找父类BaseEntity，找到返回true
	 * @param clazz
	 * @return
	 */
	public static boolean isBaseEntity(Class clazz){
		boolean isfind = false;
		//获取接口
    	Set<String> set = new HashSet<String>();
        for(;clazz!=Object.class&&null!=clazz;clazz=clazz.getSuperclass()){
        	Class<?>[] inters = clazz.getInterfaces();
        	for(Class inter:inters){
        		set.add(inter.getName());
        	}
        }
        
        //判断是否继承BaseEntity
        Iterator<String> it = set.iterator();  
        while (it.hasNext()) {  
            String name = it.next();  
            if(BaseEntity.class.getName().equals(name))
        	  isfind =true;  
        }  
		return isfind;
	} 
	
	/**
	 * 判断是否为WeakEntity基类
	 * @param clazz
	 * @return
	 */
	public static boolean isWeakEntity(Class clazz){
		boolean findit = false;
		for(;clazz!=Object.class&&null!=clazz;clazz=clazz.getSuperclass()){
			if(clazz.getName().equals(WeakEntity.class.getName())){
				findit = true;
				break;
			}
		}
		return findit;
	}
	
	/**
	 * 判断是否为StrongEntity基类
	 * @param clazz
	 * @return
	 */
	public static boolean isStrongEntity(Class clazz){
		boolean findit = false;
		for(;clazz!=Object.class&&null!=clazz;clazz=clazz.getSuperclass()){
			if(clazz.getName().equals(StrongEntity.class.getName())){
				findit = true;
				break;
			}
		}
		return findit;
	}
	
	/**
	 * 判断是否为Record基类
	 * @param clazz
	 * @return
	 */
	public static boolean isRecord(Class clazz){
		boolean findit = false;
		for(;clazz!=Object.class&&null!=clazz;clazz=clazz.getSuperclass()){
			if(clazz.getName().equals(Record.class.getName())){
				findit = true;
				break;
			}
		}
		return findit;
	}
	
	/**
	 * 字段大小写判断
	 * @param key
	 * @return
	 */
	public static String caps(String key){
		if(DatabaseRouter.getCaps()==Caps.LOWER)
			return key.toLowerCase();
		else if(DatabaseRouter.getCaps()==Caps.SENSITIVE) 
			return key;
		else if(DatabaseRouter.getCaps()==Caps.UPPER)
			return key.toUpperCase();
		else
			return key.toLowerCase();
	}
	
	/**
	 * 根据动态代理类获取强实体的class
	 * @return
	 */
	public static BaseEntity getStrongClazz(Class clazz){
		try {
			String clazzName = clazz.getName();
			if(clazzName.indexOf("$$")>0)
				clazzName = clazzName.substring(0,clazzName.indexOf("$$"));
			BaseEntity be = (BaseEntity) Class.forName(clazzName).newInstance();
			return be;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
}
