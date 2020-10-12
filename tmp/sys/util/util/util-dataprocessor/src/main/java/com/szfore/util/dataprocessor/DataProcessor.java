package com.szfore.util.dataprocessor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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


public class DataProcessor {
	
	private Map<String, Object> map = new HashMap<String, Object>();
	private List<Command> commands = new ArrayList<Command>();
	
	
	/**
	 * 将 t 中的 properties 放到 result 中
	 * @param ts
	 * @param properties
	 * @return [{property1="", property2=""}]
	 */
	public static <T> DataProcessor create(T t, String... properties)
	{
		DataProcessor dataProcessor = new DataProcessor();
		return dataProcessor.copyToMap(t, properties);
	}
	
	/**
	 * 将 t 中的 properties 放到 map 中
	 * @param t
	 * @param properties
	 * @return {property1="", property2=""}
	 */
	private <T> DataProcessor copyToMap(T t, String... properties)
	{
		map = DataUtil.copyToMap(map, t, properties);
		return this;
	}
	
	public DataProcessor execute(Command command)
	{
		commands.add(command);
		return this;
	}
	
	public DataProcessor put(String key, Object value)
	{
		map.put(key, value);
		return this;
	}
	
	public Map<String, Object> getResult() 
	{
		for (Command command : commands) 
		{
			command.execute(map);
		}
		return map;
	}

	
	/*---------------------------*/
	
	
	/**
	 * 给 result 的 properties 填充默认值 <br>
	 * property==null 或者 property == "" 或者 result 没有 property
	 * @param properties
	 * @param defaultValue
	 * @return {.., property=defaultValue, propertyA=defaultValue}
	 */
	public DataProcessor fillDefaultValue(Object defaultValue, String... properties) 
	{
		execute(new FillDefaultValue(defaultValue, properties));
		return this;
	}
	
	public DataProcessor replaceKey(String oldKey, String newKey)
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
	public <K,V> DataProcessor replaceValue(Map<K, V> newValueMap, String... selfKeys) 
	{
		execute(new ReplaceValue<K, V>(newValueMap, selfKeys));
		return this;
	}

	public DataProcessor replaceValueByNullProperty(String replacePro, Object replaceVal, String nullProperty) 
	{
		execute(new ReplaceValueByNullProperty(replacePro, replaceVal, nullProperty));
		return this;
	}

	public DataProcessor replaceValueByProperty(String replacePro, Object replaceVal, String jugePro, Object jugeVal) 
	{
		execute(new ReplaceValueByProperty(replacePro, replaceVal, jugePro, jugeVal));
		return this;
	}

	public DataProcessor replaceNullValueWithProperty(String nullValPro, String newValPro)
	{
		execute(new ReplaceNullValueWithProperty(nullValPro, newValPro));
		return this;
	}
	
	public DataProcessor formatDateValue(String formatPattern, String... properties)
	{
		execute(new DateFormater(formatPattern, properties));
		return this;
	}
	
	public DataProcessor formatDoubleValue(Integer digitalNum, String... properties) 
	{
		execute(new DoubleFormater(digitalNum, properties));
		return this;
	}
	
	public DataProcessor removeKeys(String... keys) 
	{
		execute(new RemoveKey(keys));
		return this;
	}
}
