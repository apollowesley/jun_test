package com.foo.common.base.enums;

/**
 * 完全同html的type
 * 
 * @author think
 *
 */
public enum FormFieldType {
	hidden, text,
	/**
	 * 默认3行
	 */
	textarea,
	/**
	 * 目前这个select只兼容系统字典值，所以，在使用前，请先去生产系统上查看最新可用的字典值
	 * 
	 * select的字段不会显示在表格上，显示在表格上的是tf过后的字段。tf过后的字段会自动生成
	 */
	select, select2;
}
