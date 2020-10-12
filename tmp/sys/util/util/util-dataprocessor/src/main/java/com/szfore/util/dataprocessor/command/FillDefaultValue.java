package com.szfore.util.dataprocessor.command;

import java.util.Map;

import com.szfore.util.dataprocessor.DataUtil;

public class FillDefaultValue implements Command {

	private Object defaultValue;
	private String[] properties;

	public FillDefaultValue(Object defaultValue, String... properties) 
	{
		this.defaultValue = defaultValue;
		this.properties = properties;
	}

	
	public Map<String, Object> execute(Map<String, Object> map) 
	{
		return DataUtil.fillDefaultValue(map, defaultValue, properties);
	}
}
