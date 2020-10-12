package com.szfore.util.dataprocessor.command;

import java.util.Map;

import com.szfore.util.dataprocessor.DataUtil;

public class DoubleFormater implements Command {

	private Integer digitalNum;
	private String[] properties;
	
	public DoubleFormater(Integer digitalNum, String... properties) 
	{
		this.digitalNum = digitalNum;
		this.properties = properties;
	}

	public Map<String, Object> execute(Map<String, Object> map) 
	{
		return DataUtil.formatDoubleValue(map, digitalNum, properties);
	}

}
