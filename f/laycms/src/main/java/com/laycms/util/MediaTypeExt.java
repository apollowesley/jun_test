package com.laycms.util;

import org.springframework.http.MediaType;

/**
 * @author  zbb
 * @version 创建时间：2015年11月25日 上午9:27:36
 * 类说明
 */
public class MediaTypeExt  extends MediaType{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	public final static String APPLICATION_JSON_VALUE = "application/json; charset=utf-8";
	
	public MediaTypeExt(String type) {
		super(type);
	}

}
