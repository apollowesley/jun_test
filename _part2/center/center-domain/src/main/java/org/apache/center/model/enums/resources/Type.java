package org.apache.center.model.enums.resources;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.playframework.service.CodeEnumService;

public enum Type implements CodeEnumService {

	MODULE("module", "模块"), METHOD("method", "方法");

	private Type(String key, String text) {
		this.code = key;
		this.text = text;
	}

	private String code;

	private String text;

	private static Map<String, String> map = new TreeMap<String, String>();

	public static Type getInstance() {
		return MODULE;
	}
	
	@Override
	public String getKey() {
		return "resourcesType";
	}

	@Override
	public Map<String, String> getData() {
		if (map.isEmpty()) {
			for (Type e : values()) {
				map.put(e.code, e.text);
			}
		}
		return map;
	}

	@Override
	public String getText(String code) {
		if (StringUtils.isBlank(code)) {
			return "";
		}
		if (map.isEmpty()) {
			getData();
		}
		String text = map.get(code);
		if (text == null) {
			return "";
		} else {
			return text;
		}
	}

	@Override
	public boolean isExist(String code) {
		if (StringUtils.isBlank(code)) {
			return false;
		}
		if (map.isEmpty()) {
			getData();
		}
		return map.containsKey(code);
	}

}
