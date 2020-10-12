package com.zyguo.tools.push;


import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import nsp.NSPClient;
import nsp.OAuth2Client;
import nsp.support.common.AccessToken;
import nsp.support.common.NSPException;

import com.alibaba.fastjson.JSONObject;
import com.zyguo.sdk.utils.BUtils;


/**
 * 经测试，华为，透传可以直接透到应用内，就是应用挂了，也能把应用拉活和把消息传进去
 * 华为的通知栏模式，最烂，声音振动等等均不可控
 * 所以华为采用透传模式比较好
 * @author zyguo
 *
 */
public class HuaweiPush extends AbstractAndroidPush<JSONObject, HuaweiPushResult>{
	private String appId;
	private String appKey;
	private String jksPassword;
	private String jksFile;
	private OAuth2Client oauth2Client; 
	private String _accessToken = "BlVB+c5BzbNIUCMW31YecuuUIG1pfplcGm46UFVuGA4fcUOVTBBoorI+vZE="; //充当缓存用
 
	public HuaweiPush( String appId, String appKey, String jksFile, String jksPass ) throws Exception{
		super();
		this.appId = appId;
		this.appKey = appKey;
		this.jksFile = jksFile;
		this.jksPassword = jksPass;
		log.debug("appId=" + appId + ",appKey=" + appKey + ",jksFile=" + jksFile );
		oauth2Client = new OAuth2Client();
		URL file = HuaweiPush.class.getResource("/" + jksFile );
		log.debug("file=" + file + ",stream=" + file.openStream() );
		oauth2Client.initKeyStoreStream( file.openStream(), jksPassword );
	}
	
	private NSPClient getClient() {
		NSPClient client = null;
        try {
    		client = new NSPClient( getAccessToken() );
            client.initHttpConnections(30, 50);//设置每个路由的连接数和最大连接数
            client.setTimeout(10000, 15000);
			client.initKeyStoreStream(HuaweiPush.class.getResource("/" + jksFile ).openStream(), jksPassword ); ////如果访问https必须导入证书流和密码
		} catch (Exception e) {
			log.error("华为 getClient 异常", e);
		} 
        return client;
	}
	
	private String getAccessToken() throws NSPException{
		if( BUtils.isBlank( _accessToken ) ){
			AccessToken access_token = oauth2Client.getAccessToken("client_credentials", appId, appKey );
	        log.debug("access token :" + access_token.getAccess_token() + ",expires time[access token 过期时间]:"
	                + access_token.getExpires_in() );
	         _accessToken = access_token.getAccess_token();
		}
		if( BUtils.isBlank( _accessToken ) ){
			log.error("huwei, 获取 accessToken 失败");
		}
		return _accessToken;
	} 
	
	
	@Override
	protected JSONObject pushBody2message( PushBody pushBody, boolean isToApp ){
		try{
			JSONObject json = new JSONObject();
			if( isToApp ){ //透传模式
				json.put("pushBody", pushBody );
			}else{ //通知栏模式
				json.put("notification_title", pushBody.getNoticeTitle() );
				json.put("notification_content", pushBody.getNoticeDesc() );
				if( BUtils.isNotBlank( pushBody.getAppIcon() )){
					json.put("notification_status_icon", pushBody.getAppIcon() );	
				}
				json.put("doings", 1 ); //1：直接打开应用 2：通过自定义动作打开应用
				//json.put("intent", "");
				if( pushBody.getParams() != null && pushBody.getParams().isEmpty() == false ){
					ArrayList<HashMap<String, String>> extras = new ArrayList<HashMap<String, String>>();
					extras.add( pushBody.getParams() );
					json.put("extras", extras );	
				}
			}
			return json;
		}catch( Exception e){
			log.error(" huawei, pushBody2message异常", e);
		}
		return null;
		
	}
	
	@Override
	protected String dealPushResponse( HuaweiPushResult response, PushBody pushBody ) {
		if ( response != null && response.getResultcode() == 0 ) {
			log.debug("send huawei push suceess," + pushBody );
			return SUCCESS;
		} else {
			log.error("send huawei push failed,error==" + response + "," + pushBody);
			return response.getMessage();
		}
	}

	@Override
	protected HuaweiPushResult sendPush(JSONObject msg, String token, boolean isToApp ) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
        NSPClient client = getClient();
        HuaweiPushResult result = null;
        //接口调用
        String rsp = null;
		try {
			if( isToApp ){ //透传模式
		        String requestID = "1_1362472787848";
		        //构造请求
		        hashMap.put("deviceToken", token );
		        hashMap.put("message", msg.toString() );
		        hashMap.put("priority", 0); //0 高级别，1普通级别
		        hashMap.put("cacheMode", 1);
		        //标识消息类型（缓存机制），必选
		        //由调用端赋值，取值范围（1~100）。当TMID+msgType的值一样时，仅缓存最新的一条消息
		        hashMap.put("msgType", 0);
		        //可选
		        //如果请求消息中，没有带，则MC根据ProviderID+timestamp生成，各个字段之间用下划线连接
		        hashMap.put("requestID", requestID);
		        
		        log.debug("hashmap=" + hashMap );
		        rsp = client.call("openpush.message.single_send", hashMap, String.class);
			}else{ // 通知栏模式
		        hashMap.put("push_type", 1 );
		        hashMap.put("tokens", token );
		        hashMap.put("android", msg  );
		        log.debug("hashmap=" + hashMap );
				rsp = client.call("openpush.openapi.notification_send", hashMap, String.class);
				
			}
		} catch (NSPException e) {
			log.error( e.getMessage(), e );
		} finally{
			log.debug( "huawei sendPush, rsp=" + rsp  );
			result = str2HuaweiPushResult( rsp, isToApp );
			//accessToken 过期，需要重新发送一遍
			if( result!= null && ( result.getResultcode() == 6 || result.getResultcode() == 102 )  ){
				_accessToken = null;
				client = getClient();
				try {
					if( isToApp ){
						rsp = client.call("openpush.message.single_send", hashMap, HuaweiPushResult.class);
					}else{
						rsp = client.call("openpush.openapi.notification_send", hashMap, HuaweiPushResult.class);
					}
				} catch (Exception e2) {
					log.error("huawei accesstoken 过期，重新发送照样失败", e2 );
				} finally{
					log.debug( "huawei 重新 sendPush, rsp=" + rsp  );
					result = str2HuaweiPushResult( rsp, isToApp);
				}
				
			}
		}
        
		return result;
	}
	
	private HuaweiPushResult str2HuaweiPushResult( String str, boolean isToApp ){
		if( BUtils.isBlank( str ) ){
			return null;
		}
		JSONObject rspJson = JSONObject.parseObject( str );
		HuaweiPushResult result = null;
		if( isToApp ){ //透传的返回值格式
			if( rspJson != null && rspJson.containsKey("resultcode") ){
				result = new HuaweiPushResult();
				result.setMessage( rspJson.getString("message" ));
				result.setRequestID( rspJson.getString( "requestID") );
				result.setResultcode( rspJson.getIntValue("resultcode"));
			}
		}else{ //非透传的返回值格式
			if( rspJson != null && rspJson.containsKey("result_code") ){
				result = new HuaweiPushResult();
				result.setMessage( rspJson.getString("message" ));
				result.setRequestID( rspJson.getString( "request_id") );
				result.setResultcode( rspJson.getIntValue("result_code"));
			}
			
		}
		return result;
	}
}
