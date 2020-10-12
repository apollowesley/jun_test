package org.nature.framework.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类
 * Created by Ocean on 2016/3/9.
 */
public final class ReflectionUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     */
    public static <T>T newInstance(Class<T> cls){
        T instance ;
        try {
            instance = cls.newInstance();
        } catch (Exception e) {
           LOGGER.error("new instance failure",e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object object,Method method,Object... objects){
        Object result;
        try {
            method.setAccessible(true);
            result = method.invoke(object, objects);
        } catch (Exception e) {
            LOGGER.error("invoke method "+method.getName()+" failure ",e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置成员变量的值
     */
    public static void setField(Object object,Field field,Object value){
        try {
            field.setAccessible(true);
            field.set(object,value);
        } catch (IllegalAccessException e) {
            LOGGER.error("set field value failure",e);
            throw new RuntimeException(e);
        }
    }
    
    /**
     * 根据名称获取Field
     */
    public static Field getField(Class<?> cls,String fieldName){
    	Field field = null;
    	try {
			field = cls.getField(fieldName);
			field.setAccessible(true);
		} catch (NoSuchFieldException e) {
			LOGGER.error("no such field named "+fieldName,e);
            throw new RuntimeException(e);
		} catch (SecurityException e) {
			LOGGER.error("security problem named"+fieldName,e);
            throw new RuntimeException(e);
		}
    	return field;
    }
    
    /**
     * 获取属性的值
     */
    public static Object getFieldValue(Object object,Field field){
    	Object fieldValue=null;
    	try {
    		fieldValue= field.get(object);
		} catch (Exception e) {
			LOGGER.error("getFieldValue error "+field.getName(),e);
            throw new RuntimeException(e);
		}
    	return fieldValue;
    }
    
}
