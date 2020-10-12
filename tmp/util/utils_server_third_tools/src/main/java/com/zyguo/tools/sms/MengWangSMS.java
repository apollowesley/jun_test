package com.zyguo.tools.sms;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.zyguo.sdk.utils.BUtils;


public class MengWangSMS extends AbstractSMS{
	private String url;// 梦网的域
	private String userId;// 梦网的用户ID
	private String password;// 梦网的用户密码

	@Override
	protected List<NameValuePair> getSmsParams(String areaCode, String phoneNum, String content) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();// 设置表格参数
		params.add(new BasicNameValuePair("userId", userId));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("pszMobis", phoneNum));
		params.add(new BasicNameValuePair("pszMsg", content));
		params.add(new BasicNameValuePair("iMobiCount", "1"));
		params.add(new BasicNameValuePair("pszSubPort", "*"));
		return params;
	}

	@Override
	protected String getSmsUrl( String areaCode ) {
		return this.url;
	}

	private long getOrderId( String ret ) throws Exception{
		try {
			Document doc = DocumentHelper.parseText( ret );	
			Element root = doc.getRootElement();
			String v = root.getStringValue();
			return BUtils.isBlank( v ) ? 0 :  Long.valueOf( v );
		} catch (Exception e) {
			throw e;
		} 
	}
	
	@Override
	protected String dealSendResponse(HttpResponse resp, String phoneNum, String content ) {
		try {
			HttpEntity entity = resp.getEntity();// 响应实体/内容
			String ret = EntityUtils.toString(entity, "gbk");
			log.debug("meng wang ret=" + ret + ", phoneNum=" + phoneNum  + ",content=" + content );
			if( BUtils.isNotBlank( ret ) ){
				long orderId = getOrderId(ret);
				log.debug("meng wang orderId=" + orderId + ", phoneNum=" + phoneNum  + ",content=" + content );
				if( orderId > 0 ){
					return SUCCESS;
				}else{
					return ret;
				}
			}else{
				return "meng wang response content is null";
			}
		} catch (Exception e) {
			System.out.println("meng wang exception, phoneNum=" + phoneNum + ",content=" + content + ",e=" + e.getMessage() ) ;
			log.error("meng wang exception, phoneNum=" + phoneNum + ",content=" + content, e);
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
}
