package com.jason.utils.email.exception;

/**
 * 邮件发送失败的超级异常类
 * @author jason
 *
 */
public class MailSenderException extends MailException {
	private static final long serialVersionUID = 1L;
	
	public MailSenderException(){
		super();
	}
	
    public MailSenderException(String message) {
        super(message);
    }
    
    public MailSenderException(Throwable cause) {
        super(cause);
    }
}