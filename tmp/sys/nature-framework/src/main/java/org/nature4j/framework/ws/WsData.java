package org.nature4j.framework.ws;

public class WsData {
	private String key;
	private Object value;
	
	public WsData() {

	}
	public WsData(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	
	
}
