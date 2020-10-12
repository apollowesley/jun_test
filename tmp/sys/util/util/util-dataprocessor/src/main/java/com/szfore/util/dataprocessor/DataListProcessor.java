package com.szfore.util.dataprocessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jodd.typeconverter.Convert;

import com.szfore.util.basic.ListUtil;
import com.szfore.util.dataprocessor.command.AddValueBySelfProperty;
import com.szfore.util.dataprocessor.command.Command;
import com.szfore.util.dataprocessor.command.DateFormater;
import com.szfore.util.dataprocessor.command.DoubleFormater;
import com.szfore.util.dataprocessor.command.FillDefaultValue;
import com.szfore.util.dataprocessor.command.RemoveKey;
import com.szfore.util.dataprocessor.command.ReplaceKey;
import com.szfore.util.dataprocessor.command.ReplaceNullValueWithProperty;
import com.szfore.util.dataprocessor.command.ReplaceValue;
import com.szfore.util.dataprocessor.command.ReplaceValueByNullProperty;
import com.szfore.util.dataprocessor.command.ReplaceValueByProperty;



public class DataListProcessor {
	
	private static final String DEFAULT_VALUE = null;
	private List<Map<String, Object>> resultList = new ArrayList<Map<String,Object>>();
	
	private List<Command> commands = new ArrayList<Command>();
	
	public DataListProcessor execute(Command command)
	{
		commands.add(command);
		return this;
	}
	
	/**
	 * 将 t 中的 properties 放到 result 中
	 * @param ts
	 * @param properties
	 * @return [{property1="", property2=""}]
	 */
	public static <T> DataListProcessor create(List<T> ts, String... properties)
	{
		DataListProcessor result = new DataListProcessor();
		return result.copyToMaps(ts, properties);
	}
	
	/**
	 * 将 t 中的 properties 放到 result 中
	 * @param ts
	 * @param properties
	 * @return [{property1="", property2=""}]
	 */
	private <T> DataListProcessor copyToMaps(List<T> ts, String... properties)
	{
		if(ts == null || ts.isEmpty())
		{
			return this;
		}
		
		for (T t : ts) 
		{
			Map<String, Object> map = new HashMap<String, Object>();
			DataUtil.copyToMap(map, t, properties);
			resultList.add(map);
		}
		return this;
	}
	
	/**
	 * 根据 sharedKey ，将每个 t 的 properties 添加到对应的Result中 <br>
	 * t 为空，使用默认""值，返回 [{.. , property1="", property2=""}] <br>
	 * t.property 为空，使用默认""值，返回 [{.., property=""}]
	 * @param ts 
	 * @param sharedKey  t 和 result 的共同属性
	 * @param properties 要放入Result中的属性
	 * @return [{.. , property1="value1", property2="value2"}]
	 */
	public <T, K> DataListProcessor addToEveryResult(List<T> ts, String sharedKey, Class<K> classOfSharedKey, String... properties)
	{
		if(ts == null || ts.isEmpty())
		{
			for (Map<String, Object> result : resultList) 
			{
				DataUtil.fillDefaultValue(result, DEFAULT_VALUE, properties);
			}
			return this;
		}
		
		Map<K, T> mapOft = ListUtil.listToMap(ts, sharedKey, classOfSharedKey);
		for (Map<String, Object> result : resultList) 
		{
			T t = mapOft.get(result.get(sharedKey));
			if(t != null)
			{
				result = DataUtil.copyToMap(result, t, properties);
				continue;
			}
			
			//将 "" 值放到result中
			for (String property : properties) 
			{
				result.put(property, DEFAULT_VALUE);
			}
		}
		return this;
	}
	
	public List<Map<String, Object>> getResultList() 
	{
		for (Map<String, Object> result : resultList) 
		{
			for (Command command : getCommands()) 
			{
				command.execute(result);
			}
		}
		return resultList;
	}
	
	private List<Command> getCommands()
	{
		return commands;
	}
	
	
	

	public DataListProcessor addDividedResult(Map<?, ?> divisorMap, 
										 Integer dividend, 
										 String newProName, 
										 String shareKey) 
	{
		for (Map<String, Object> result : resultList) 
		{
			if(dividend <= 0)
			{
				result.put(newProName, 0.0);
				continue;
			}
			
			Object divisorKey = result.get(shareKey);
			Object divisor = divisorMap.get(divisorKey);
			divisor = divisor == null ? 0.0 : divisor;
			Double dividResult = Convert.toDouble(divisor) / Convert.toDouble(dividend);
			result.put(newProName, dividResult);
		}
		return this;
	}
	
	
	
	/*---------------------------*/
	
	
	
	
	/**
	 * 给 result 的 properties 填充默认值 <br>
	 * property==null 或者 property == "" 或者 result 没有 property
	 * @param properties
	 * @param defaultValue
	 * @return {.., property=defaultValue, propertyA=defaultValue}
	 */
	public DataListProcessor fillDefaultValue(Object defaultValue, String... properties) 
	{
		execute(new FillDefaultValue(defaultValue, properties));
		return this;
	}
	
	public DataListProcessor replaceKey(String oldKey, String newKey)
	{
		execute(new ReplaceKey(oldKey, newKey));
		return this;
	}
	
	/**
	 * 将结果记录中 key 键对应的值，替换为 newValueMap 中的值
	 * @param newValueMap {1="完成"}
	 * @param key "status"
	 * @return {.., status=1} 替换之后变成 {.., status="完成"}
	 */
	public <K,V> DataListProcessor replaceValue(Map<K, V> newValueMap, String... selfKeys) 
	{
		execute(new ReplaceValue<K, V>(newValueMap, selfKeys));
		return this;
	}

	public DataListProcessor replaceValueByNullProperty(String replacePro, Object replaceVal, String nullProperty) 
	{
		execute(new ReplaceValueByNullProperty(replacePro, replaceVal, nullProperty));
		return this;
	}

	public DataListProcessor replaceValueByProperty(String replacePro, Object replaceVal, String jugePro, Object jugeVal) 
	{
		execute(new ReplaceValueByProperty(replacePro, replaceVal, jugePro, jugeVal));
		return this;
	}

	public DataListProcessor replaceNullValueWithProperty(String nullValPro, String newValPro)
	{
		execute(new ReplaceNullValueWithProperty(nullValPro, newValPro));
		return this;
	}
	
	public DataListProcessor formatDateValue(String formatPattern, String... properties)
	{
		execute(new DateFormater(formatPattern, properties));
		return this;
	}
	
	public DataListProcessor formatDoubleValue(Integer digitalNum, String... properties) 
	{
		execute(new DoubleFormater(digitalNum, properties));
		return this;
	}
	
	public DataListProcessor removeKeys(String... keys) 
	{
		execute(new RemoveKey(keys));
		return this;
	}
	
	public <K, V> DataListProcessor addValueBySelfProperty(Map<K, V> valueMap, String propertyName, String selfProperty) 
	{
		execute(new AddValueBySelfProperty(valueMap, propertyName, selfProperty));
		return this;
	}
}
