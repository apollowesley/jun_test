package org.apache.center.service.exception;

import org.apache.playframework.exception.MessagePromptException;

/**
 * 消息提示异常类，抛出异常提示消息
 * @author 20160405
 *
 */
public class CenterMessagePromptException extends MessagePromptException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -520626417940537710L;

	public CenterMessagePromptException(String code) {
		super(code);
	}

	public CenterMessagePromptException(String code, String message) {
		super(code, message);
	}
	

}
