package com.zyguo.tools.sms;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.zyguo.sdk.utils.BUtils;


public class YiMeiSMS extends AbstractSMS{
	private String url;// 亿美的域
	private String userId;// 亿美的用户ID
	private String password;// 亿美的用户密码
	private String gjUrl;// 亿美的域
	private String gjUserId;// 亿美的用户ID
	private String gjPassword;// 亿美的用户密码
	
	@Override
	protected List<NameValuePair> getSmsParams(String areaCode, String phoneNum, String content) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();// 设置表格参数
		if ( AREA_CODE_CHINA.equals( areaCode ) ) {
			params.add(new BasicNameValuePair("cdkey", userId));
			params.add(new BasicNameValuePair("password", password));
		} else {
			phoneNum = "00" + areaCode + phoneNum;
			params.add(new BasicNameValuePair("cdkey", gjUserId));
			params.add(new BasicNameValuePair("password", gjPassword));
		}
		params.add(new BasicNameValuePair("phone", phoneNum));
		params.add(new BasicNameValuePair("message", content));
		params.add(new BasicNameValuePair("addserial", (int) (Math.random() * 10000) + ""));
		params.add(new BasicNameValuePair("timestamps", System.currentTimeMillis() + ""));
		return params;
	}

	@Override
	protected String getSmsUrl( String areaCode ) {
		if ( AREA_CODE_CHINA.equals( areaCode ) ) {
			return this.url;
		}else{
			return this.gjUrl;
		}
	}

	@Override
	protected String dealSendResponse(HttpResponse resp, String phoneNum, String content ) {
		try {
			HttpEntity entity = resp.getEntity();// 响应实体/内容
			String ret = EntityUtils.toString(entity, "gbk");
			log.debug("yimei ret：" + ret + ",phoneNum=" + phoneNum + ",content=" + content );
			if ( BUtils.isNotBlank( ret ) ) {
				if( ret.indexOf("<error>0</error>") > 0  ){
					return SUCCESS;	
				}else{
					return ret;	
				}
			}else{
				return "yimei response content is null";
			}
		} catch (Exception e) {
			log.error("yimei exception, phoneNum=" + phoneNum + ",content=" + content, e);
			return "exception, e=" + e.getMessage();
		}
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

	public void setGjUserId(String gjUserId) {
		this.gjUserId = gjUserId;
	}

	public void setGjPassword(String gjPassword) {
		this.gjPassword = gjPassword;
	}

	public void setGjUrl(String gjUrl) {
		this.gjUrl = gjUrl;
	}
	

}
