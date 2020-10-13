package com.bodsite.demo.exception;

import com.bodsite.common.exception.BaseException;

public class DemoException extends BaseException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DemoException(DEMO_EXPECTION ex,Throwable cause){
		super(ex.getCode(),ex.getMessage(),cause);
	}
	
	public DemoException(DEMO_EXPECTION ex){
		super(ex.getCode(),ex.getMessage());
	}
	
	public DemoException(int code,String message){
		super(code,message);
	}
	
	public DemoException(String message,Throwable cause){
		super(BaseException.ERROR_CODE,message,cause);
	}
	
	public DemoException(String message){
		super(BaseException.ERROR_CODE,message);
	}
	
	public DemoException(){
		super();
	}
	
	public enum DEMO_EXPECTION{
		MEMBER_NOT_FOUND(600001,"会员无效！");
		private DEMO_EXPECTION(int code, String message) {
			this.code = code;
			this.message = message;
		}
		private int code;
		private String message;
		public int getCode() {
			return code;
		}
		public String getMessage() {
			return message;
		}
	}
}
