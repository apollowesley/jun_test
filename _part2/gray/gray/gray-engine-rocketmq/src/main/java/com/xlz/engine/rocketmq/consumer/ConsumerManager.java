package com.xlz.engine.rocketmq.consumer;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.client.consumer.DefaultMQPushConsumer;
import com.alibaba.rocketmq.client.exception.MQClientException;
import com.alibaba.rocketmq.client.impl.consumer.DefaultMQPushConsumerImpl;
import com.alibaba.rocketmq.common.ServiceState;
import com.alibaba.rocketmq.common.constant.LoggerName;
import com.alibaba.rocketmq.common.protocol.heartbeat.SubscriptionData;
import com.xlz.engine.common.result.Result;
import com.xlz.engine.common.result.ResultCode;
import com.xlz.engine.common.utils.HttpUtil;

public class ConsumerManager {
	private static final Logger logger = LoggerFactory.getLogger(LoggerName.BrokerLoggerName);
	// 配置的时候需要传值进来
	private static String GRAY_SERVER = "http://localhost:8080/gray/pullRocketmqConsumer/config";
	private static String APP_ID = "paygent";

	private final ConcurrentHashMap<String, DefaultMQPushConsumer> consumerTable = new ConcurrentHashMap<String, DefaultMQPushConsumer>(
			100);
	private static ConsumerManager instance = new ConsumerManager();
	private final Timer timer = new Timer();
	private AtomicInteger syncFalg = new AtomicInteger(0);

	private static final String TOPIC = "TOPIC";
	private static final String TAGS = "TAGS";
	private static String currentIp;
	static {
		try {
			setCurrentIp();
		} catch (SocketException e) {
			logger.error("获取本机ip异常，灰度无法使用，请检查", e);
		}
	}

	private ConsumerManager() {
		timer.schedule(new TimerTask() {
			public void run() {
				if (syncFalg.get() == 0) {
					syncFalg.getAndSet(1);
					syncGrayServer();
					syncFalg.getAndSet(0);
				}
			}
		}, 1000 * 10, 1000 * 10);
	}

	public static ConsumerManager getInstance() {
		return instance;
	}

	public void registerConsumer(DefaultMQPushConsumer consumer) {
		// 得到consumer的ip，topic，tag作为key,以$$分割
		Map<String, String> topicTags = getTopicAndTags(consumer);

		String key = getConsumerKey(topicTags.get(TOPIC), topicTags.get(TAGS));
		System.out.println(key);
		consumerTable.put(key, consumer);
	}

	/**
	 * 从consumer中获取topic和tags
	 * 
	 * @param consumer
	 * @return
	 */
	private Map<String, String> getTopicAndTags(DefaultMQPushConsumer consumer) {
		Map<String, String> topicAndTags = new HashMap<String, String>();
		ConcurrentHashMap<String, SubscriptionData> mapp = consumer.getDefaultMQPushConsumerImpl()
				.getSubscriptionInner();
		for (Map.Entry<String, SubscriptionData> entry : mapp.entrySet()) {
			if (entry.getKey() != null && entry.getKey().indexOf("RETRY") == -1) {
				String topic = entry.getValue().getTopic();
				String tags = "";
				topicAndTags.put(TOPIC, topic);
				for (String tag : entry.getValue().getTagsSet()) {
					tags += tag + "|";
				}
				if (tags.endsWith("|")) {
					tags = tags.substring(0, tags.length() - 1);
				}
				topicAndTags.put(TAGS, tags);
				break;
			}
		}
		return topicAndTags;
	}

	/**
	 * consumer 灰度情况设置
	 * 
	 * @param isGray
	 *            是否设置成灰度
	 * @param key
	 *            consumer的key
	 * @param filterPackage
	 *            过滤类的包名
	 * @param filterCode
	 *            过滤类的代码
	 * @return 是否设置成功
	 */
	private boolean consumerGraySetting(DefaultMQPushConsumer consumer, boolean isGray, String key,String filterNormalPackage,
			String filterGrayPackage, String normalFilterCode, String grayFilterCode, String devlopGrayIp) {
		// 从key里面获取topic和tags
		String topicTags[] = key.split("\\u0024\\u0024");
		String topic = topicTags[1];
		String tags = topicTags[2];
		String filterPackage = null;
		try {
			// 获取上一次的topic、group、tag
			String group = consumer.getConsumerGroup();
			// 如果客户端和服务端的状态一致则直接返回
			if ((isGray && group.endsWith("_GRAY")) 
					|| (!isGray && group.endsWith("_NORMAL"))
					|| (!isGray && !group.endsWith("_GRAY") && !group.endsWith("_NORMAL"))) {
				logger.info("不需要重启更改灰度策略");
				System.out.println("不需要重启更改灰度策略");
				return true;
			}
			logger.info("不需要重启更改灰度策略");
			System.out.println("=====================");
			// 判断是否需要切换
			String filterCode = null;
			// 如果没有启动启动灰度
			if (!isGray) {
				group = group.replace("_GRAY", "").replace("_NORMAL", "");
			} else {
				// 根据ip判断本机是否为灰度机
				if (devlopGrayIp != null && devlopGrayIp.indexOf(currentIp) != -1) {
					// 切换为灰度
					group = group + "_GRAY";
					filterCode = grayFilterCode;
					filterPackage = filterGrayPackage;
				} else {
					group = group + "_NORMAL";
					filterCode = normalFilterCode;
					filterPackage = filterNormalPackage;
				}
				// 替换filterCode的变量值
				filterCode = filterCode.replace("CONSUMER_ID", key);
				filterCode = filterCode.replace("APP_ID", APP_ID);
			}

			consumer.shutdown();
			consumer.setConsumerGroup(group);
			if (!isGray) {
				consumer.subscribe(topic, tags);
			} else {
				System.out.println(filterPackage);
				System.out.println(filterCode);
				consumer.subscribe(topic, filterPackage, filterCode);
			}
			DefaultMQPushConsumerImpl dcc = consumer.getDefaultMQPushConsumerImpl();
			dcc.setServiceState(ServiceState.CREATE_JUST);
			dcc.getmQClientFactory().registerConsumer(group, dcc);
			consumer.start();
		} catch (MQClientException e) {
			logger.error("设置灰度异常：", e);
			return false;
		}
		return true;
	}

	private void syncGrayServer() {
		try {
			// 1、获取服务端配置
			String json = HttpUtil.sendGet(GRAY_SERVER + "?applicationId=" + APP_ID, "UTF-8");
			// 2、配置解析
			Result<Map<String, String>> result = JSON.parseObject(json, Result.class);
			if (result.isSuccess() && result.getCode().equals(ResultCode.COMMON_SUCCESS.getCode())) {
				// 循环对当前应用的所有consumer进行灰度配置
				for (Map.Entry<String, DefaultMQPushConsumer> entry : consumerTable.entrySet()) {
					Map<String, String> config = result.getObj();
					boolean isGray = Boolean.valueOf(config.get("is_gray"));
					String filterGrayPackage = config.get("filter_gray_package");
					String filterNormalPackage = config.get("filter_normal_package");
					String filterPackage = config.get("filter_package");
					String normalFilterCode = config.get("filter_normal_code");
					String grayFilterCode = config.get("filter_gray_code");
					String devlopGrayIp = config.get("gray_rocketmq");

					consumerGraySetting(entry.getValue(), isGray, entry.getKey(), 
							filterNormalPackage,filterGrayPackage, normalFilterCode,
							grayFilterCode, devlopGrayIp);
				}
			} else {
				logger.error("获取服务端的数据异常：");
			}
		} catch (IOException e) {
			logger.error("请求灰度服务端网络异常：", e);
		} 
	}

	private String getConsumerKey(String topic, String tags) {
		return APP_ID + "$$" + topic + "$$" + tags;
	}

	private static void setCurrentIp() throws SocketException {
		Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
		InetAddress ip = null;
		while (allNetInterfaces.hasMoreElements()) {
			NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
			Enumeration addresses = netInterface.getInetAddresses();
			while (addresses.hasMoreElements()) {
				ip = (InetAddress) addresses.nextElement();
				if (ip != null && ip instanceof Inet4Address) {
					currentIp = ip.getHostAddress();
				}
			}
		}
	}

}
