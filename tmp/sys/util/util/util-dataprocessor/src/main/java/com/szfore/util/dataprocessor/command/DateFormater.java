package com.szfore.util.dataprocessor.command;

import java.util.Map;

import com.szfore.util.dataprocessor.DataUtil;

public class DateFormater implements Command {

	private String formatPattern;
	private String[] properties;
	
	public DateFormater(String formatPattern, String... properties) 
	{
		this.formatPattern = formatPattern;
		this.properties = properties;
	}

	public Map<String, Object> execute(Map<String, Object> map) 
	{
		return DataUtil.formatDateValue(map, formatPattern, properties);
	}

}
