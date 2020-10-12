package com.site.util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * bean转换工具类 
 * <p>Title: BeanUtil</p>
 * <p>Description : </p>
 * <p>Company: Rayootech.</p>
 * @author  chenkl
 * @date    2015-4-25
 */
public class BeanUtil {

    /** 
     * 将一个 JavaBean 对象转化为一个  Map 
     * @param bean 要转化的JavaBean 对象 
     * @return 转化出来的  Map 对象 
     * @throws IntrospectionException 如果分析类属性失败 
     * @throws IllegalAccessException 如果实例化 JavaBean 失败 
     * @throws InvocationTargetException 如果调用属性的 setter 方法失败 
     */ 
    public static Map<String, Object> convertBean(Object bean) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        try {
            Class<?> type = bean.getClass();
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (!propertyName.equals("class")) {
                    Method readMethod = descriptor.getReadMethod();
                    Object result = readMethod.invoke(bean, new Object[0]);
                    returnMap.put(propertyName, result);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return returnMap;
    }
    
    /** 
     * 将一个 Map 对象转化为一个 JavaBean 
     * @param type 要转化的类型 
     * @param map 包含属性值的 map 
     * @return 转化出来的 JavaBean 对象 
     * @throws IntrospectionException 
     *             如果分析类属性失败 
     * @throws IllegalAccessException 
     *             如果实例化 JavaBean 失败 
     * @throws InstantiationException 
     *             如果实例化 JavaBean 失败 
     * @throws InvocationTargetException 
     *             如果调用属性的 setter 方法失败 
     */ 
    public static Object convertMap(Class<?> type, Map<String, Object> map) {
        Object obj = null; 
        try {
            obj = type.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(type); 
            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors(); 
            for (int i = 0; i< propertyDescriptors.length; i++) { 
                PropertyDescriptor descriptor = propertyDescriptors[i]; 
                String propertyName = descriptor.getName(); 
                if (map.containsKey(propertyName)) { 
                    Object value = map.get(propertyName); 
                    Object[] args = new Object[1]; 
                    args[0] = value; 
                    descriptor.getWriteMethod().invoke(obj, args); 
                } 
            } 
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return obj; 
    } 

}
