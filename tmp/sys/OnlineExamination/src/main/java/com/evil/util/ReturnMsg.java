package com.evil.util;

/**
 * 操作提示辅助类
 * @author evil
 */
public class ReturnMsg {
	
	public static final int SUCCESS = 200;//正确
	public static final int WARN = 201;//警告
	public static final int ERROR = 300;//错误
	public static final int TIME_OUT = 301;//超时
	
	private String statusCode=SUCCESS+"";   //响应状态  200成功，300失败 ，301超时
	private String message="操作成功！";      //提示信息
	private String callbackType="forward"; //closeCurrent,forward
	private String forwardUrl="";   //callbackType是forward时使用
	private String navTabId="";	 //操作成功后需要指定navTab时使用
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
