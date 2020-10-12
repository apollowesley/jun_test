package com.siweifu.utils;

import java.util.Map;

import com.jfinal.plugin.activerecord.CPI;
import com.jfinal.plugin.activerecord.Model;
import com.jfinal.plugin.activerecord.Record;

/**
 * Model工具类
 * @title ModelUtils.java
 * @description 
 * @company 北京思维夫网络科技有限公司
 * @url http://www.meitimao.com
 * @author 卢春梦
 * @version 1.0
 * @created 2015年5月3日下午5:19:08
 */
public class ModelUtils {

	/**
	 * copy 老model的属性到新model
	 * @param src 源model
	 * @param dist 新model
	 */
	public static void copy(Model<?> src, Model<?> dist) {
		dist._setAttrs(CPI.getAttrs(src));
	}

	/**
	 * copy 老model的属性到新Record
	 * @param src 源model
	 * @param dist 新Record
	 */
	public static void copy(Model<?> src, Record dist) {
		dist.setColumns(src);
	}

	/**
	 * copy 老Record的属性到新model
	 * @param src 源Record
	 * @param dist 新model
	 */
	public static void copy(Record src, Model<?> dist) {
		dist._setAttrs(src.getColumns());
	}

	/**
	 * copy java Bean到model
	 * @param src
	 * @param dist
	 */
	@SuppressWarnings("unchecked")
	public static void copy(Object src, Model<?> dist) {
		Map<String, Object> attrs = BeanUtils.toMap(src);
		dist._setAttrs(attrs);
	}

	/**
	 * copy java Bean到record
	 * @param src
	 * @param dist
	 */
	@SuppressWarnings("unchecked")
	public static void copy(Object src, Record dist) {
		Map<String, Object> columns = BeanUtils.toMap(src);
		dist.setColumns(columns);
	}
}
