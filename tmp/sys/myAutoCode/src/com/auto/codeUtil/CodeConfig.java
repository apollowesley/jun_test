package com.auto.codeUtil;

public class CodeConfig {
	public static final String version = "v1.0.0";
	private String lowerOrUpper = "0";// 0:小写，1：大写,2:不改变
	private String encode = "GBK";// 字符编码
	private Boolean addIsMents = true;// 是否添加注视,xml文件不添加注视,XMXL才添加
	private String paramPrefix = "";// pp_

	public String getLowerOrUpper() {
		return lowerOrUpper;
	}

	public void setLowerOrUpper(String lowerOrUpper) {
		this.lowerOrUpper = lowerOrUpper;
	}

	public String getEncode() {
		return encode;
	}

	public void setEncode(String encode) {
		this.encode = encode;
	}

	public Boolean getAddIsMents() {
		return addIsMents;
	}

	public void setAddIsMents(Boolean addIsMents) {
		this.addIsMents = addIsMents;
	}

	public String getParamPrefix() {
		return paramPrefix;
	}

	public void setParamPrefix(String paramPrefix) {
		this.paramPrefix = paramPrefix;
	}

}
