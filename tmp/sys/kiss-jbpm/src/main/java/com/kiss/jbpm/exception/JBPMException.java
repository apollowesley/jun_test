package com.kiss.jbpm.exception;

import com.kiss.exception.ApplicationException;

public class JBPMException extends ApplicationException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4401396420388582599L;

	public JBPMException() {
		super();
	}

	public JBPMException(Exception e) {
		super(e);
	}

	public JBPMException(String message, Exception e) {
		super(message, e);
	}

	public JBPMException(String errorCode, String[] params) {
		super(errorCode, params);
	}

	public JBPMException(String message) {
		super(message);
	}
	
}
