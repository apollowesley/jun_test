package com.loong.dilib.exception;

/**
 * Api注释异常
 */
public class DIAnnotationException extends DIException {

	private static final long serialVersionUID = -4625791898707502733L;

	/**
	 * 构造方法
	 */
	public DIAnnotationException() {

		super();
	}

	/**
	 * 构造方法
	 * 
	 * @param message 异常信息
	 */
	public DIAnnotationException(String message) {

		super(message);
	}

	/**
	 * 构造方法
	 * 
	 * @param e 异常对象
	 */
	public DIAnnotationException(Exception e) {

		super(e);
	}

	/**
	 * 构造方法
	 * 
	 * @param message 异常信息
	 * @param e 异常对象
	 */
	public DIAnnotationException(String message, Exception e) {

		super(message, e);
	}
}
