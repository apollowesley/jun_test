package com.szfore.util.dataprocessor.command;

import java.util.Map;

import com.szfore.util.dataprocessor.DataUtil;

public class AddValueBySelfProperty implements Command {

	private Map<?, ?> valueMap;
	private String propertyName;
	private String selfProperty;
	
	
	public AddValueBySelfProperty(Map<?, ?> valueMap, String propertyName, String selfProperty) 
	{
		this.valueMap = valueMap;
		this.propertyName = propertyName;
		this.selfProperty = selfProperty;
	}

	
	public Map<String, Object> execute(Map<String, Object> map) 
	{
		return DataUtil.addValueBySelfProperty(map, valueMap, propertyName, selfProperty);
	}

}
