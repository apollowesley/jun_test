package org.apache.center.model.enums.user;

import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.apache.playframework.service.CodeEnumService;

public enum Gender implements CodeEnumService {

	MALE("male", "男"), FEMALE("female", "女");

	private Gender(String key, String text) {
		this.code = key;
		this.text = text;
	}

	private String code;

	private String text;

	private static Map<String, String> map = new TreeMap<String, String>();

	public static Gender getInstance() {
		return MALE;
	}
	
	@Override
	public String getKey() {
		return "userGender";
	}

	@Override
	public Map<String, String> getData() {
		if (map.isEmpty()) {
			for (Gender e : values()) {
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
