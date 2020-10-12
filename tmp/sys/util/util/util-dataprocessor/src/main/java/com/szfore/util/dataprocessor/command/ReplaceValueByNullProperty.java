package com.szfore.util.dataprocessor.command;

import java.util.Map;

import com.szfore.util.dataprocessor.DataUtil;

public class ReplaceValueByNullProperty implements Command {

	private String replacePro;
	private Object replaceVal;
	private String nullProperty;
	
	public ReplaceValueByNullProperty(String replacePro, Object replaceVal, String nullProperty) 
	{
		this.replacePro = replacePro;
		this.replaceVal = replaceVal;
		this.nullProperty = nullProperty;
	}


	public Map<String, Object> execute(Map<String, Object> map) 
	{
		return DataUtil.replaceValueByNullProperty(map, replacePro, replaceVal, nullProperty);
	}

}
