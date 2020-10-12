package com.foo.common.base.enums;

public enum FormFieldValidation {
	/**
	 * 只要引入了empty，所有验证规则会被自动抛弃
	 */
	empty,
	/**
	 * 是否是必填字段
	 */
	required,
	/**
	 * 手机号码验证
	 */
	mobile;
}
