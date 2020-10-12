/**
 * 
 * @auther wuwang
 * @createTime 2014-11-29 下午10:35:26
 */
package com.staticClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsDateJsonValueProcessor;

/**
 * 
 * 
 * @author peaches
 */
public class JsonDateValueProcessor extends JsDateJsonValueProcessor {
	private String code1 = "yyyy-MM-dd HH:mm";
	private String code2 = "yyyy-MM-dd HH:mm:ss";
	private String code3 = "yyyy-MM-dd HH:mm:ss.S";
	/**
	 * datePattern
	 */
	private String datePattern = "yyyy-MM-dd HH:mm";

	/**
	 * JsonDateValueProcessor
	 */
	public JsonDateValueProcessor() {
		super();
	}

	/**
	 * @param format
	 */
	public JsonDateValueProcessor(String format) {
		super();
		datePattern = format;
	}

	public JsonDateValueProcessor(int code) {
		super();
		switch (code) {
		case 1:
			datePattern = code1;
		case 2:
			datePattern = code2;
		case 3:
			datePattern = code3;
		}
	}

	/**
	 * @param value
	 * @param jsonConfig
	 * @return Object
	 */
	@Override
	public Object processArrayValue(Object value, JsonConfig jsonConfig) {
		return process(value);
	}

	/**
	 * @param key
	 * @param value
	 * @param jsonConfig
	 * @return Object
	 */
	@Override
	public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
		return process(value);
	}

	/**
	 * process
	 * 
	 * @param value
	 * @return
	 */
	private Object process(Object value) {
		try {
			if (value instanceof Date) {
				SimpleDateFormat sdf = new SimpleDateFormat(datePattern, Locale.CHINA);
				return sdf.format((Date) value);
			}
			return value == null ? "" : value.toString();
		} catch (Exception e) {
			return "";
		}

	}

	/**
	 * @return the datePattern
	 */
	public String getDatePattern() {
		return datePattern;
	}

	/**
	 * @param pDatePattern
	 *            the datePattern to set
	 */
	public void setDatePattern(String pDatePattern) {
		datePattern = pDatePattern;
	}

}
