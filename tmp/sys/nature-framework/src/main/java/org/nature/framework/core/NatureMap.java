package org.nature.framework.core;

import java.util.HashMap;

import org.nature.framework.util.CastUtil;

public class NatureMap extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public String getString(String key) {
		return CastUtil.castString(this.get(key));
	}

	public String getString(String key, String defaultValue) {
		return CastUtil.castString(this.get(key), defaultValue);
	}

	public int getInt(String key) {
		return CastUtil.castInt(this.get(key));
	}

	public int getInt(String key, int defaultValue) {
		return CastUtil.castInt(this.get(key), defaultValue);
	}

	public long getLong(String key) {
		return CastUtil.castLong(this.get(key));
	}

	public long getLong(String key, long defaultValue) {
		return CastUtil.castLong(this.get(key), defaultValue);
	}

	public double getDouble(String key) {
		return CastUtil.castDouble(this.get(key));
	}

	public double getDouble(String key, double defaultValue) {
		return CastUtil.castDouble(this.get(key), defaultValue);
	}

	public float getFloat(String key) {
		return CastUtil.castFloat(this.get(key));
	}

	public float getFloat(String key, float defaultValue) {
		return CastUtil.castFloat(this.get(key), defaultValue);
	}

	public boolean getBoolean(String key) {
		return CastUtil.castBoolean(this.get(key));
	}

	public boolean getBoolean(String key, boolean defaultValue) {
		return CastUtil.castBoolean(this.get(key), defaultValue);
	}
	/**
	 * 将同类或父类对象吸入到实体内（吸星大法）
	 */
	public void suck(NatureMap natureMap){
		this.putAll(natureMap);
	}
	
}
