package com.weiwo.jpush;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.Notification;
import cn.jpush.api.push.model.notification.Notification.Builder;

import com.hibernate.voDao.JpushLog;

public class PushMessage {
	protected static final Logger LOG = LoggerFactory.getLogger(PushMessage.class);

	// demo App defined in resources/jpush-api.conf
	private static final String appKey = "da7175add8b2936fc2163d8f";
	private static final String masterSecret = "0c894816c3a704c74642bbbf";

	public enum MessageAction {
		ADD_FRIENDS, CONFIRM_FRIENDS, REPLY, CHATMESSAGE
	}

	public String title;
	public String alert;
	public String content;
	public String alias;
	public String tag;
	public HashMap extra;
	public String action;

	public static void main(String[] args) {
		new PushMessage().testSendPush();
	}

	private void testSendPush() {
		// HttpProxy proxy = new HttpProxy("localhost", 3128);
		// Can use this https proxy: https://github.com/Exa-Networks/exaproxy
		JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);

		// For push, all you need do is to build PushPayload object.
		PushPayload payload = buildPushObject_android_alias();

		try {
			PushResult result = jpushClient.sendPush(payload);
			LOG.info("Got result - " + result);

		} catch (APIConnectionException e) {
			JpushLogUtil.saveLog(new JpushLog(payload.getSendno(), payload.toString(), "", "", (short) 0, (short) 1));
			LOG.error("Connection error. Should retry later. ", e);

		} catch (APIRequestException e) {
			JpushLogUtil.saveLog(new JpushLog(payload.getSendno(), payload.toString(), String.valueOf(e.getErrorCode()), e.getMessage(), (short) 0, (short) 1));
			LOG.error("Error response from JPush server. Should review and fix it. ", e);
			LOG.info("HTTP Status: " + e.getStatus());
			LOG.info("Error Code: " + e.getErrorCode());
			LOG.info("Error Message: " + e.getErrorMessage());
			LOG.info("Msg ID: " + e.getMsgId());
		}
	}

	/**
	 * action请使用枚举类 MessageAction
	 * 
	 * @param title
	 * @param content
	 * @param alias
	 * @param action
	 * @createTime 2014-12-15 下午10:11:31
	 */
	public void sendPush(String title, String content, String alias, String action) {
		this.title = title;
		this.content = content;
		this.alias = alias;
		this.action = action;
		System.out.println("--1--");
		sendPush();
	}

	public void sendPush(String title, String content, String alias, HashMap extra, String action) {
		this.title = title;
		this.content = content;
		this.alias = alias;
		this.action = action;
		this.extra = extra;
		System.out.println("--2--");
		sendPush();
	}

	private void sendPush() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);

				PushPayload payload = buildPushObject_android_alias();

				try {
					PushResult result = jpushClient.sendPush(payload);
					LOG.info("Got result - " + result);

				} catch (APIConnectionException e) {
					JpushLogUtil.saveLog(new JpushLog(payload.getSendno(), payload.toString(), "", "", (short) 0, (short) 1));
					LOG.error("Connection error. Should retry later. ", e);

				} catch (APIRequestException e) {
					JpushLogUtil.saveLog(new JpushLog(payload.getSendno(), payload.toString(), String.valueOf(e.getErrorCode()), e.getMessage(), (short) 0, (short) 1));
					LOG.error("Error response from JPush server. Should review and fix it. ", e);
					LOG.info("HTTP Status: " + e.getStatus());
					LOG.info("Error Code: " + e.getErrorCode());
					LOG.info("Error Message: " + e.getErrorMessage());
					LOG.info("Msg ID: " + e.getMsgId());
					LOG.info("Msg ID2: " + e.getMessage());
				}

			}
		}).start();
	}

	private PushPayload buildPushObject_android_alias() {
		cn.jpush.api.push.model.notification.AndroidNotification.Builder builder = AndroidNotification.newBuilder().setAlert(content).setTitle(title).addExtra("ACTION", action);

		if (extra != null) {
			builder.addExtras(extra);
		}

		Builder builderN = Notification.newBuilder().addPlatformNotification(builder.build());

		return PushPayload.newBuilder().setPlatform(Platform.android()).setAudience(Audience.alias(alias)).setNotification(builderN.build()).build();
	}
}
