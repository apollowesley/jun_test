package com.loong.dilib.exception;

/**
 * 接口访问失败异常
 */
public class DIConnectException extends DIException {

	private static final long serialVersionUID = -4348259807570172469L;

	/**
	 * 构造方法
	 */
	public DIConnectException() {

		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param message 异常信息
	 */
	public DIConnectException(String message) {

		super(message);
	}

	/**
	 * 构造方法
	 * 
	 * @param e 异常对象
	 */
	public DIConnectException(Exception e) {

		super(e);
	}

	/**
	 * 构造方法
	 * 
	 * @param message 异常信息
	 * @param e 异常对象
	 */
	public DIConnectException(String message, Exception e) {

		super(message, e);
	}
}
