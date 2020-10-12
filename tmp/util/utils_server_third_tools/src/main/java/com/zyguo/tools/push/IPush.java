package com.zyguo.tools.push;


public interface IPush{
	/**
	 * 异步发送PUSH
	 */
	public boolean asynSendPush( PushBody pushBody );
	
	/**
	 * 同步发送PUSH
	 * @return 返回错误提示文案，若为 success， 说明发送成功
	 */
	public String syncSendPush( PushBody pushBody, int maxWaitSec );
}
