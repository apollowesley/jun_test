package com.evil.util;

/**
 * ������ʾ������
 * @author evil
 */
public class ReturnMsg {
	
	public static final int SUCCESS = 200;//��ȷ
	public static final int WARN = 201;//����
	public static final int ERROR = 300;//����
	public static final int TIME_OUT = 301;//��ʱ
	
	private String statusCode=SUCCESS+"";   //��Ӧ״̬  200�ɹ���300ʧ�� ��301��ʱ
	private String message="�����ɹ���";      //��ʾ��Ϣ
	private String callbackType="forward"; //closeCurrent,forward
	private String forwardUrl="";   //callbackType��forwardʱʹ��
	private String navTabId="";	 //�����ɹ�����Ҫָ��navTabʱʹ��
	private String rel="";
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCallbackType() {
		return callbackType;
	}
	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}
	public String getForwardUrl() {
		return forwardUrl;
	}
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
	public String getNavTabId() {
		return navTabId;
	}
	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}	
	
	public static ReturnMsg SUCCESSMsg(){
		return new ReturnMsg();
	}
	
	public static ReturnMsg ERRORMsg(String meg){
		ReturnMsg rm=new ReturnMsg();
		rm.setMessage(meg);
		rm.setStatusCode(ReturnMsg.ERROR+"");
		rm.setCallbackType("");
		return rm;
	}
}
