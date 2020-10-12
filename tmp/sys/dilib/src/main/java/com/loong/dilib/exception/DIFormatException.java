package com.loong.dilib.exception;

/**
 * 格式化异常
 *
 * @author 张成轩
 */
public class DIFormatException extends DIException {

	private static final long serialVersionUID = -681434925694076966L;

	/**
	 * 构造方法
	 */
	public DIFormatException() {

		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param message 异常信息
	 */
	public DIFormatException(String message) {

		super(message);
	}

	/**
	 * 构造方法
	 * 
	 * @param e 异常对象
	 */
	public DIFormatException(Exception e) {

		super(e);
	}

	/**
	 * 构造方法
	 * 
	 * @param message 异常信息
	 * @param e 异常对象
	 */
	public DIFormatException(String message, Exception e) {

		super(message, e);
	}
}
