package com.autoscript.ui.helper;

/**
 * 模板工具类异常
 * @author dingyi
 */
public class TemplateHelperException extends Exception {

	private static final long serialVersionUID = -3396327940551990677L;
	
	public TemplateHelperException(){
		super();
	}
		
	public TemplateHelperException(String msg){
		super(msg);
	}
	
	public TemplateHelperException(String msg,Throwable t){
		super(msg, t);
	}
		
	public TemplateHelperException(Throwable t){
		super(t);
	}
}
