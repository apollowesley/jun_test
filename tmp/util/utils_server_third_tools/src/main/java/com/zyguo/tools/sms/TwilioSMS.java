package com.zyguo.tools.sms;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import com.zyguo.sdk.utils.BUtils;


public class TwilioSMS extends AbstractSMS{
	private String url;// 梦网的域
	private String userId;// 梦网的用户ID
	private String password;// 梦网的用户密码

	protected List<NameValuePair> getSmsParams(String areaCode, String phoneNum, String content) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("To", "+" + areaCode + phoneNum ));
		params.add(new BasicNameValuePair("From", userId));
		params.add(new BasicNameValuePair("Body", content));
		return params;
	}
	
	//不需要
	protected String getSmsUrl( String areaCode ) {
		return this.url;
	}
	
	protected String sendSMS(String areaCode, String phoneNum, String content) {
		TwilioRestClient client = new TwilioRestClient( url, password );
		MessageFactory messageFactory = client.getAccount().getMessageFactory();
		long begin = System.currentTimeMillis();
		List<NameValuePair> params = null;
		String result;
		try {
			params = this.getSmsParams(areaCode, phoneNum, content);
			Message message = messageFactory.create( params );
			log.debug("twilio ret=" + ( message == null ? -1 : message.getSid() ) + ",params=" + params );
			if( message == null ){
				result = "twilio no response";
			}else{
				if( BUtils.isNotBlank( message.getSid() ) ){
					result = SUCCESS;
				}else{
					result = message.getSid();
				}
			}
			log.debug("result=" + result + ",phoneNum=" + phoneNum + ",content=" + content );
			return result;
		} catch (TwilioRestException e) {
			log.error("twilio sms exception：" , e );
			return "twilio exception, " + e.getMessage();
		} finally{
			long end =  System.currentTimeMillis();
			log.debug("sendSMS 耗时=" + ( end - begin ) + "毫秒" + ",url=" + url + ",params=" + params);
		}
	} 
	
	//不需要
	protected String dealSendResponse(HttpResponse resp, String phoneNum, String content ) {
		return null;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
