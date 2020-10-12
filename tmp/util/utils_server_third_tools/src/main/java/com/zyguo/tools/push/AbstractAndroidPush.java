package com.zyguo.tools.push;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.zyguo.sdk.common.AbstractTask;
import com.zyguo.sdk.utils.BUtils;

public abstract class AbstractAndroidPush<M, R extends Object> extends AbstractTask<String> implements IAndroidPush {
	protected Logger log = Logger.getLogger( this.getClass().getName() );
	public final static String SUCCESS = "success";

	private String sendPush(PushBody pushBody, boolean isToApp ) {
		try {
			if ( pushBody != null && BUtils.isNotBlank( pushBody.getToken() ) ) {
				long begin = System.currentTimeMillis();
				log.debug("begin send push:" + pushBody );
				M msg = pushBody2message( pushBody, isToApp );
				R response = sendPush( msg, pushBody.getToken(), isToApp  );
				long end = System.currentTimeMillis();
				log.debug("end push，result==" + response + ",耗时：" + ( end - begin ) + "毫秒");
				if ( response != null ) {
					String result = dealPushResponse( response, pushBody );
					log.debug("send push result=," + result + "," + pushBody );
					return result;
				} else {
					log.error("no_response," + pushBody);
					return "no_response";
				}
			} else {
				return "token is null";
			}
		} catch (Exception e) {
			log.error("send xiaomi push exception", e);
			return "exception:" + e.getMessage();
		}
		
	}

	protected abstract String dealPushResponse( R response, PushBody pushBody );
	protected abstract M pushBody2message( PushBody pushBody, boolean isToApp );
	protected abstract R sendPush( M msg, String token, boolean isToApp );
	
	@Override
	public boolean asynSendPush( final PushBody pushBody) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				sendPush( pushBody, false );
			}
		};
		return asynDoTask( runnable );
	}
	
	@Override
	public String syncSendPush( final PushBody pushBody, int maxWaitSec ) {
		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				return sendPush( pushBody, false );
			}
		};
		try {
			return syncDoTask( callable, maxWaitSec );
		} catch (Exception e) {
			log.error( "sendPushBySync, future.get 异常, pushbody=" + pushBody, e);
		} 
		return null;
	}


	@Override
	public boolean asynSendPushToApp(final PushBody pushBody) {
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				sendPush( pushBody, true );
			}
		};
		return asynDoTask( runnable );
	}


	@Override
	public String syncSendPushToApp(final PushBody pushBody, int maxWaitSec) {
		Callable<String> callable = new Callable<String>() {
			@Override
			public String call() throws Exception {
				return sendPush( pushBody, true );
			}
		};
		try {
			return syncDoTask( callable, maxWaitSec );
		} catch (Exception e) {
			log.error( "sendPushBySync, future.get 异常, pushbody=" + pushBody, e);
		} 
		return null;
	}

}
