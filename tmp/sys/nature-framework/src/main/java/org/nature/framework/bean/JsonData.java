package org.nature.framework.bean;

import java.io.Serializable;

import com.alibaba.fastjson.JSONObject;

public class JsonData implements Serializable{
	private static final long serialVersionUID = 1L;
	public String json = "";

	public JsonData(Object object) {
		this.json = JSONObject.toJSONString(object);
	}
	
}
