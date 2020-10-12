package com.szfore.util.dataprocessor;

import java.util.Date;
import java.util.Map;

import jodd.bean.BeanUtil;
import jodd.typeconverter.Convert;

import com.szfore.util.basic.DateUtil;
import com.szfore.util.basic.NumberUtil;


public class DataUtil {
	
	private static final String DEFAULT_VALUE = null;
	
	/**
	 * 将 t 中的 properties 放到 map 中
	 * @param t
	 * @param properties
	 * @return {property1="", property2=""}
	 */
	public static <T> Map<String, Object> copyToMap(Map<String, Object> map, T t, String... properties)
	{
		for (String property : properties) 
		{
			Object value = BeanUtil.getPropertySilently(t, property);
			value = value == null ? DEFAULT_VALUE : value;
			map.put(property, value);
		}
		return map;
	}
		
	
	/**
	 * 给 result 的 properties 填充默认值 <br>
	 * property==null 或者 property == "" 或者 result 没有 property
	 * @param properties
	 * @param defaultValue
	 * @return {.., property=defaultValue, propertyA=defaultValue}
	 */
	public static Map<String, Object> fillDefaultValue(Map<String, Object> map, Object defaultValue, String... properties) 
	{
		for (String property : properties) 
		{
			if(!map.containsKey(property) || map.get(property) == null || "".equals(map.get(property)))
			{
				map.put(property, defaultValue);
			}
		}
		return map;
	}
	
	
	public static Map<String, Object> formatDateValue(Map<String, Object> map, String formatOfDate, String... properties)
	{
		for (String property : properties) 
		{
			Object value = map.get(property);
			if(value != null && value instanceof Date)
			{
				map.put(property, DateUtil.dateFormat(formatOfDate, (Date)value));
			}
		}
		return map;
	}

	public static Map<String, Object> replaceKey(Map<String, Object> map, String oldKey, String newKey)
	{
		if(map.containsKey(oldKey))
		{
			Object value = map.remove(oldKey);
			map.put(newKey, value);
		}
		return map;
	}
	
	/**
	 * 将结果记录中 key 键对应的值，替换为 newValueMap 中的值
	 * @param newValueMap {1="完成"}
	 * @param key "status"
	 * @return {.., status=1} 替换之后变成 {.., status="完成"}
	 */
	public static <K,V> Map<String, Object> replaceValue(Map<String, Object> map, Map<K, V> newValueMap, String... selfKeys) 
	{
		if(newValueMap == null || newValueMap.isEmpty())
		{
			return map;
		}
		
		for (String key : selfKeys) 
		{
			Object value = map.get(key);
			Object newValue = newValueMap.get(value);
			map.put(key, newValue);
		}
		return map;
	}

	public static Map<String, Object> replaceValueByNullProperty(Map<String, Object> map, String replacePro, Object replaceVal, String nullProperty) 
	{
		Object value = map.get(nullProperty);
		if(value == null)
		{
			map.put(replacePro, replaceVal);
		}
		return map;
	}

	public static Map<String, Object> replaceValueByProperty(Map<String, Object> map, String replacePro, Object replaceVal, String jugePro, Object jugeVal) 
	{
		Object value = map.get(jugePro);
		if(value != null && value.equals(jugeVal))
		{
			map.put(replacePro, replaceVal);
		}
		return map;
	}

	public static Map<String, Object> formatDoubleValue(Map<String, Object> map, Integer digitalNum, String... properties) 
	{
		for (String property : properties) 
		{
			Object value = map.get(property);
			if(value != null)
			{
				map.put(property, Convert.toDouble(NumberUtil.doubleFormat((Double)value, digitalNum, digitalNum)));
			}
		}
		return map;
	}
	
	public static <K, V> Map<String, Object> addValueBySelfProperty(Map<String, Object> map, Map<K, V> srcValueMap, String newKey, String selfProperty)
	{
		Object targetKey = map.get(selfProperty);
		V value = srcValueMap.get(targetKey);
		map.put(newKey, value);
		return map;
	}
}
