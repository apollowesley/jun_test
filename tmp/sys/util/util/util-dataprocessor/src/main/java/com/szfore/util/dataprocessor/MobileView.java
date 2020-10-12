package com.szfore.util.dataprocessor;

import java.util.HashMap;
import java.util.Map;

import com.szfore.util.basic.JsonUtil;

public class MobileView {
	
	public static final String STATUS_CODE = "code";
	public static final String MESSAGE_CODE = "msg";
	public static final String CUSTOM_DATA_CODE = "data";

	public static final String SUCCESS = "1";
	public static final String ERROR = "0";
	
	private Map<String, Object> fixData = new HashMap<String, Object>();
	private Map<String, Object> customData = new HashMap<String, Object>();
	
	public MobileView(){}
	
	public MobileView put(String key, Object value)
	{
		customData.put(key, value);
		return this;
	}
	
	public MobileView setData(Map<String, Object> data)
	{
		this.customData.putAll(data);
		return this;
	}
	
	public String returnSuccess(String message)
	{
		fixData.put(STATUS_CODE, SUCCESS);
		fixData.put(MESSAGE_CODE, message);
		return toJson();
	}
	
	public String returnSuccess()
	{
		return returnSuccess("");
	}
	
	public String returnError(String message)
	{
		fixData.put(STATUS_CODE, ERROR);
		fixData.put(MESSAGE_CODE, message);
		return toJson();
	}
	
	public String returnError()
	{
		return returnError("");
	}
	
	private String toJson() 
	{
		if(customData != null && !customData.isEmpty())
		{
			fixData.put(CUSTOM_DATA_CODE, customData);
		}
		return JsonUtil.toJson(fixData);
	}
}
