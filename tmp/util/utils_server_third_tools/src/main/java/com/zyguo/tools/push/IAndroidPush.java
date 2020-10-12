package com.zyguo.tools.push;


public interface IAndroidPush{
	/**
	 * 异步发送PUSH（到通知栏模式）
	 */
	public boolean asynSendPush( PushBody pushBody );
	
	/**
	 * 同步发送PUSH （到通知栏模式）
	 * @return 返回错误提示文案，若为 success， 说明发送成功
	 */
	public String syncSendPush( PushBody pushBody, int maxWaitSec );
	
	/**
	 * 异步发送PUSH（透传模式,传到应用内）
	 * @param pushBody
	 * @return
	 */
	public boolean asynSendPushToApp( PushBody pushBody );
	
	/**
	 * 同步发送PUSH （透传模式,传到应用内）
	 * @return 返回错误提示文案，若为 success， 说明发送成功
	 */
	public String syncSendPushToApp( PushBody pushBody, int maxWaitSec );
	
}
