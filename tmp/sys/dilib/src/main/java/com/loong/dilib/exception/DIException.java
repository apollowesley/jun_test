package com.loong.dilib.exception;

/**
 * 接口异常父类
 *
 * @author 张成轩
 */
public class DIException extends RuntimeException {

	private static final long serialVersionUID = 5062214140636316099L;

	/**
	 * 构造方法
	 */
	public DIException() {

		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param message 异常信息
	 */
	public DIException(String message) {

		super(message);
	}

	/**
	 * 构造方法
	 * 
	 * @param e 异常对象
	 */
	public DIException(Exception e) {

		super(e);
	}

	/**
	 * 构造方法
	 * 
	 * @param message 异常信息
	 * @param e 异常对象
	 */
	public DIException(String message, Exception e) {

		super(message, e);
	}
}
