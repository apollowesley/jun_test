package com.zyguo.tools.push;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONObject;
import com.zyguo.sdk.utils.BUtils;



/**
 * 经测试，友盟透传只有当应用活着的时候可以直接透到应用内，如果应用挂了，这次的透传只能把应用拉活而已，但是消息透传不进来
 * 友盟的通知栏，可以控制声音，振动，但是不能做分类覆盖，只支持覆盖模式或全部平铺模式，
 * 所以综合考虑，友盟采用通知栏覆盖逻辑
 * @author zyguo
 *
 */
public class UmengPush extends AbstractAndroidPush<JSONObject, HttpResponse>{
	private String url = "http://msg.umeng.com/api/send";
	private String USER_AGENT = "Mozilla/5.0";
	private static String appKey;
	private static String masterSecret;
	
	@Override
	protected String dealPushResponse(HttpResponse response, PushBody pushBody)  {
		try {
			HttpEntity entity = response.getEntity();// 响应实体/内容
			String ret = EntityUtils.toString(entity, "gbk");
			log.debug("umeng ret=" + ret);
			if( BUtils.isNotBlank( ret )  || ret.indexOf( "SUCCESS") > 0 ){
				return SUCCESS; 
			}
			return "umeng push fail, ret=" + ret;
		} catch (Exception e) {
			log.error("umeng dealPushResponse 异常", e);
			return "umeng dealPushResponse Exception ";
		}
	}

	@Override
	protected JSONObject pushBody2message(PushBody pushBody, boolean isToApp ) {
		JSONObject payload = new JSONObject();
		JSONObject body = new JSONObject();
		if( isToApp ){ //透传模式
			payload.put("display_type", "message");
			body.put("custom", pushBody);
		}else{ // 通知模式
			payload.put("display_type", "notification");
			body.put("title", pushBody.getNoticeTitle());
			body.put("ticker", pushBody.getTicker());
			body.put("text", pushBody.getNoticeDesc());
			body.put("icon", "");						//小图标
			body.put("largeIcon", "");				//大图标
			body.put("sound", pushBody.getToneUrl());						//声音文件
			body.put("after_open", "go_app");								//点击通知栏后处理方式
			body.put("sound", pushBody.getToneUrl());						//声音文件
			body.put("play_vibrate", pushBody.isOpenVibration()+"");			//是否开启震动
			body.put("play_lights", pushBody.isOpenLight()+"");				//是否开启呼吸灯
			body.put("play_sound", pushBody.isOpenTone()+"");					//是否开启提示音
			
		}
		payload.put("body", body);
		
		long timestamp = System.currentTimeMillis();
		String validationToken = DigestUtils.md5Hex(appKey.toLowerCase() + masterSecret.toLowerCase() + timestamp);
		JSONObject json = new JSONObject();
		json.put("appkey", appKey);
		json.put("timestamp", timestamp );
		json.put("type", "unicast" );										//单播模式
		json.put("payload", payload);
		json.put("validation_token", validationToken );
		if( pushBody.getParams() != null && pushBody.getParams().isEmpty() == false ){
			json.put("extra", pushBody.getParams());	
		}
		json.put("device_tokens", pushBody.getToken());				//device_token的方式
		//json.put("alias", pushBody.getToken());					//别名的方式
		return json;
	}

	@Override
	protected HttpResponse sendPush( JSONObject msg, String token, boolean isToApp ) {
		try {
			HttpClient client = new DefaultHttpClient();
	        String postBody = msg.toString();
	        log.debug("postBody=" + postBody );
	        //String sign = DigestUtils.md5Hex(("POST" + url + postBody + masterSecret).getBytes("utf8"));
	        //url = url + sign;
	        log.debug("url=" + url );
	        HttpPost post = new HttpPost(url);
	        post.setHeader("User-Agent", USER_AGENT );
	        StringEntity se = new StringEntity( postBody, "UTF-8" );
	        post.setEntity(se);
	        // Send the post request and get the response
	        HttpResponse response = client.execute(post);
	        return response;
			
		} catch (Exception e) {
			log.error("umeng sendPush 异常", e);
		}
		return null;
	}
	
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public void setMasterSecret(String masterSecret) {
		this.masterSecret = masterSecret;
	}

	

	
}
