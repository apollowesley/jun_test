package com.jason.utils.email.exception;
/**
 * sender实例化时异常
 * @author Administrator
 *
 */
public class SenderInstanceException extends MailException{
	private static final long serialVersionUID = 1L;
	
	public final static String GATEWAY_NULL="无法识别邮件发送服务器网关类型";
	
	public SenderInstanceException(String message) {
		super(message);
	}
    
    public SenderInstanceException(Throwable cause) {
        super(cause);
    }

}