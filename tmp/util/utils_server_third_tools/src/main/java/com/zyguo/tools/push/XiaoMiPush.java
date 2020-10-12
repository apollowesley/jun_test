package com.zyguo.tools.push;


import com.xiaomi.push.sdk.ErrorCode;
import com.xiaomi.xmpush.server.Constants;
import com.xiaomi.xmpush.server.Message;
import com.xiaomi.xmpush.server.Message.Builder;
import com.xiaomi.xmpush.server.Result;
import com.xiaomi.xmpush.server.Sender;


/**
 * 经测试，小米，透传只能在应用活着的时候透传进来，但是应用不在了， 就不能透传进来
 * 小米的通知栏最牛B，既可以控制声音，振动，也支持覆盖模式，分类覆盖模式，平铺模式
 * 综合考虑，因为要结合业务，所以采用通知栏覆盖模式
 * @author zyguo
 *
 */
public class XiaoMiPush extends AbstractAndroidPush<Message, Result>{
	private String appKey;
	private String packageName;
 
	@Override
	protected Message pushBody2message( PushBody pushBody, boolean isToApp ){
		int noticeType = computeNoticeType( pushBody );
		Builder b = new Builder();
		b.title( pushBody.getNoticeTitle() ); 				// 标题
		b.description( pushBody.getNoticeDesc() ); 			// 描述
		//b.payload( pushBody.getPayload() ); 				// 消息主体
		b.passThrough( Message.PASS_THROUGH_NOTIFICATION );	// 非透传消息
		if( isToApp ){
			b.passThrough( Message.PASS_THROUGH_PASS );		//透传消息
		}
		b.restrictedPackageName(packageName); 				// packageName
		b.timeToLive( 7*24*3600 ); 							// 服务器保留时间1周
		b.notifyType( noticeType );							// 使用全提示（声音，震动，呼吸灯）
		b.extra( "ticker", pushBody.getTicker() );
		if( pushBody.getParams() != null ){
			for (String k : pushBody.getParams().keySet()) {
				String v = pushBody.getParams().get( k  );
				b.extra(k, v);
			}
		}
		//控制通知的分类，ID，如果ID一样，是覆盖的逻辑，如果不一样，就是一个新的通知
		//因为做的是通知栏覆盖逻辑，所以不需要设置这个
		//b.notifyId( pushBody.getNoticeType() );				
		return b.build();
	}
	
	private int computeNoticeType( PushBody pushBody ){
		if( pushBody.isOpenTone() && pushBody.isOpenVibration() ){
			return Message.NOTIFY_TYPE_ALL;		//开声音，开振动				
		}
		if( pushBody.isOpenTone() && ! pushBody.isOpenVibration() ) {
			return Message.NOTIFY_TYPE_SOUND; 	//只开声音
		}
		if( !pushBody.isOpenTone() == false && pushBody.isOpenVibration() ){
			return Message.NOTIFY_TYPE_VIBRATE;	//只开振动
		}
		if( !( pushBody.isOpenTone() && pushBody.isOpenVibration() ) ){
			return Message.NOTIFY_TYPE_LIGHTS;	//不开声音也不开振动，那就只能给亮灯提示
		}
		return Message.NOTIFY_TYPE_ALL;
	}

	@Override
	protected String dealPushResponse( Result response, PushBody pushBody ) {
		if ( ErrorCode.Success == response.getErrorCode() ) {
			log.debug("send xiaomi push suceess," + pushBody );
			return SUCCESS;
		} else {
			log.error("send xiaomi push failed,error==" + response.getReason() + "," + pushBody);
			return response.getReason();
		}
	}

	@Override
	protected Result sendPush(Message msg, String token, boolean isToApp ) {
		try {
			Constants.useOfficial();
			Sender sender = new Sender( appKey );
			//return sender.send( msg, token, 0 ); //送消息到指定设备上，不重试。通过 regId 发送
			return sender.sendToAlias( msg, token, 0); //送消息到指定设备上，不重试。通过 alias 发送
		} catch (Exception e) {
			log.error("xiaomi sendPush 异常", e);
		} 
		return null;
	}
	
	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
