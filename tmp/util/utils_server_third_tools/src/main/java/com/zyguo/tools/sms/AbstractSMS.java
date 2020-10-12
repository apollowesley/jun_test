package com.zyguo.tools.sms;

import java.util.List;
import java.util.concurrent.Callable;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.log4j.Logger;

import com.zyguo.sdk.common.AbstractTask;
import com.zyguo.sdk.http.HttpThreadPool;

public abstract class AbstractSMS extends AbstractTask<String> implements ISMS{
	protected Logger log = Logger.getLogger( this.getClass().getName() );
	public final static String AREA_CODE_CHINA = "86";
	public final static String SUCCESS = "success";

	protected String sendSMS(String areaCode, String phoneNum, String content) {
		List<NameValuePair> params = getSmsParams( areaCode, phoneNum, content );
		String url = getSmsUrl( areaCode );
		HttpResponse resp = HttpThreadPool.post( url, params );
		if( resp == null ){
			return "error , no response, photoNum=" + phoneNum + ",content=" + content;
		}else{
			String result = dealSendResponse( resp, phoneNum, content );
			log.debug("result=" + result + ",phoneNum=" + phoneNum + ",content=" + content );
			return result;
		}
	}

	@Override
	public boolean sendSMSByAsyn(final String areaCode, final String phoneNum, final String content) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				sendSMS(areaCode, phoneNum, content);
			}
		};
		return asynDoTask( runnable );
	}
	
	@Override
	public String sendSMSBySync(final String areaCode, final String phoneNum, final String content, int maxWaitSec ) {
		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				return sendSMS(areaCode, phoneNum, content);
			}
		};
		
		try {
			return syncDoTask( callable, maxWaitSec);
		} catch (Exception e) {
			log.error("sendSMSBySync, future.get 异常, phoneNum=" + phoneNum + ", content=" + content, e);
		} 
		return null;
	}

	
	protected abstract List<NameValuePair> getSmsParams(String areaCode, String phoneNum, String content);
	protected abstract String getSmsUrl( String areaCode );
	protected abstract String dealSendResponse(HttpResponse resp, String phoneNum, String content );	
	
}
