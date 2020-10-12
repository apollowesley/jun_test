package com.zyguo.tools.sms;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.zyguo.sdk.utils.BUtils;


public class NexmoSMS extends AbstractSMS{
	private String key;
	private String secret;
	private String company;
	private String url;
	private String usa;

	@Override
	protected List<NameValuePair> getSmsParams(String areaCode, String phoneNum, String content) {
        List<NameValuePair> params = new ArrayList<NameValuePair>();  
        params.add(new BasicNameValuePair("api_key", key));
        params.add(new BasicNameValuePair("api_secret", secret ));
        params.add(new BasicNameValuePair("type", "text"));
        //若为美国或者加拿大 区号相同 需要买服务 每天最多发500条
        String from = "1".equals( areaCode) ? usa :company  ;
        params.add(new BasicNameValuePair("from", from ));
        params.add(new BasicNameValuePair("to", phoneNum)); 
        params.add(new BasicNameValuePair("text", content)); 
		return params;
	}

	@Override
	protected String getSmsUrl( String areaCode ) {
		return this.url;
	}

	@Override
	protected String dealSendResponse(HttpResponse resp, String phoneNum, String content ) {
		try {
			HttpEntity entity = resp.getEntity();// 响应实体/内容
			String ret = EntityUtils.toString(entity, "gbk");
			log.debug("nexmo ret：" + ret + ",phoneNum=" + phoneNum + ",content=" + content );
			if ( BUtils.isNotBlank( ret ) ) {
				if( ret.indexOf("\"status\":\"0\"") > 0 ){
					return SUCCESS;	
				}else{
					return ret;	
				}
			}else{
				return "nexmo response content is null";
			}
		} catch (Exception e) {
			log.error("nexmo exception, phoneNum=" + phoneNum + ",content=" + content, e);
			return "exception, e=" + e.getMessage();
		}
	}
	
	public void setUrl(String url) {
		this.url = url;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public void setUsa(String usa) {
		this.usa = usa;
	}
}
