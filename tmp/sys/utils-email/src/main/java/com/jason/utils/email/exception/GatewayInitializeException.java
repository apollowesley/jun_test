package com.jason.utils.email.exception;

public class GatewayInitializeException extends MailException{
	private static final long serialVersionUID = 1L;

	public GatewayInitializeException(){
		super();
	}
	
    public GatewayInitializeException(String message) {
        super(message);
    }
    
    public GatewayInitializeException(Throwable cause) {
        super(cause);
    }
}
