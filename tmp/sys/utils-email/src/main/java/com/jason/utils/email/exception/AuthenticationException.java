package com.jason.utils.email.exception;

public class AuthenticationException extends SenderInstanceException{
	private static final long serialVersionUID = 1L;
	
	public final static String ACCOUNT_NULL="账号不能为空";
	
	public final static String PASSWORD_WRONG="密码错误";
	
	public AuthenticationException(String message) {
		super(message);
	}
    
    public AuthenticationException(Throwable cause) {
        super(cause);
    }
}