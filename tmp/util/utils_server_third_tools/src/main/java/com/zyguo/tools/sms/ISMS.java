package com.zyguo.tools.sms;


public interface ISMS {
	/**
	 * 异步发送短信
	 * @param areaCode 区号，默认86，代表中国
	 * @param phoneNum 手机号
	 * @param content  发送的内容
	 */
	public boolean sendSMSByAsyn( String areaCode, String phoneNum, String content);
	
	/**
	 * 同步发送短信
	 * @param areaCode 区号，默认86，代表中国
	 * @param phoneNum 手机号
	 * @param content  发送的内容
	 * @return 返回错误提示文案，若为 success， 说明发送成功
	 */
	public String sendSMSBySync( String areaCode, String phoneNum, String content, int maxWaitSec );
}
