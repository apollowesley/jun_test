package com.jason.utils.email.exception;

/**
 * 邮件发送失败的超级异常类
 * @author jason
 *
 */
public class MailMessageException extends MailException {
	private static final long serialVersionUID = 1L;
	
	public MailMessageException(){
		super();
	}
	
    public MailMessageException(String message) {
        super(message);
    }
    
    public MailMessageException(Throwable cause) {
        super(cause);
    }
}