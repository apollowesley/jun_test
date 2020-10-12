package org.nature4j.framework.restful;

import java.util.List;

public class RestBean {
	private String targetKey;
	private String regexKey;
	private List<String> names;
	public RestBean(String targetKey, String regexKey, List<String> names) {
		super();
		this.targetKey = targetKey;
		this.regexKey = regexKey;
		this.names = names;
	}
	public String getTargetKey() {
		return targetKey;
	}
	public void setTargetKey(String targetKey) {
		this.targetKey = targetKey;
	}
	public String getRegexKey() {
		return regexKey;
	}
	public void setRegexKey(String regexKey) {
		this.regexKey = regexKey;
	}
	public List<String> getNames() {
		return names;
	}
	public void setNames(List<String> names) {
		this.names = names;
	}
	public String toString() {
		return "RestBean [targetKey=" + targetKey + ", regexKey=" + regexKey + ", names=" + names + "]";
	}

	

}
