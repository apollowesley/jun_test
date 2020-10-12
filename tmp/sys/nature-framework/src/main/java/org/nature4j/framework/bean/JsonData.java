package org.nature4j.framework.bean;

import java.io.Serializable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JsonData implements Serializable{
	private static final long serialVersionUID = 1L;
	static Logger LOGGER = LoggerFactory.getLogger(JsonData.class);
	public String json = "";

	public JsonData(Object object) {
		this.json = JSONObject.toJSONString(object);
	}

	public JsonData(Object object,SerializerFeature...features) {
		this.json = JSONObject.toJSONString(object, features);
	}

	@Override
	public String toString() {
		return "JsonData [json=" + json + "]";
	}
}
