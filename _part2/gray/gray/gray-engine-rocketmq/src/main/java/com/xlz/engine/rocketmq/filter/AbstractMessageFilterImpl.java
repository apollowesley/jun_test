package com.xlz.engine.rocketmq.filter;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.rocketmq.common.message.MessageExt;
import com.xlz.engine.common.config.Way;
import com.xlz.engine.common.model.Application;
import com.xlz.engine.common.model.GrayDataCache;
import com.xlz.engine.common.model.Strategy;
import com.xlz.engine.rocketmq.server.GrayConfigAgent;
import com.xlz.engine.rocketmq.util.SerializableUtil;

public abstract class AbstractMessageFilterImpl {

	private final static Logger logger = LoggerFactory.getLogger(AbstractMessageFilterImpl.class);
	
	private GrayConfigAgent agentCache = GrayConfigAgent.getInstance();

	public boolean myMatch(MessageExt msg) {
		// 按照tag进行判断
		// 只有tag满足条件才进行后续的filter，否则直接返回false
		if (msg.getTags().equals("*")
				|| containsTag(msg.getTags())) {
			//判断应用是否启用灰度，可能服务端和客户端同步较慢，所以如果mq服务端未同步数据则全部返回false
			Application application = GrayDataCache.getInstance().getApplication(getApplicationId());
			if(application == null){
				return false;
			}
			
			// 获取策略，根据不同的策略进行灰度匹配
			Strategy strategy = agentCache.getGrayStrategy(getConsumerId(),getApplicationId());
			if (Way.whitelist == strategy.getWay() || Way.regular ==strategy.getWay()) {
				// 白名单、正则
				// 进行灰度属性匹配
				
				Map message = messageConvert(msg.getBody());
				//反序列化异常直接返回false，由正常consumer进行消费
				if(message ==null)
					return false;
				// 获取到灰度设置的属性对应msg的值
				String param = agentCache.getGrayParam(getConsumerId(), getApplicationId());
				String value = getMessageParamValue(message, param);
				// 匹配白名单
				if (Way.regular == strategy.getWay()) {
					// 获取正则表达式(可考虑成放在cache中，不用每次都compile)
					Pattern regular = agentCache.getGrayRegular(getConsumerId(), getApplicationId());
					Matcher matcher = regular.matcher(value);
					// 字符串是否与正则表达式相匹配
					return matcher.find() == filterResult();
				} else {
					// 查找白名单
					return containsWhitelist(value, param) == filterResult();
				}
			} else if (Way.flowTatio == strategy.getWay()) {
				// 小流量
				
			}
		}

		return false == filterResult();
	}

	protected abstract boolean filterResult();
	protected abstract String getConsumerId();
	protected abstract String getApplicationId();
	
	private Map messageConvert(byte[] msg){
		Map message = null;
		try {
			message = new HashMap();
			Object object = SerializableUtil.toObject(msg);
			Field[] fields = object.getClass().getDeclaredFields();
			for(Field field : fields){
				message.put(field.getName(), field.get(object));
			}
		} catch (Exception e) {
			//e.printStackTrace();
			//如果转换异常进行json数据反序列化
			String msgStr = new String(msg);
			message = JSON.parseObject(msgStr, Map.class);
		}
		return message;
	}
	/**
	 * 判断messge中包含的tags是否属于当前的consumer缓存中配置的tags
	 * @param msgTags
	 * @return
	 */
	public boolean containsTag(String msgTags) {
		//String tags = agentCache.getCurrentExtendParam(getConsumerId(),getApplicationId()).get("TAGS");
		String topicTags[] = getConsumerId().split("\\u0024\\u0024");
		String tags = topicTags[2];
		for (String tag : tags.split("|")) {
			if (msgTags.indexOf(tag) != -1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 从json message中提取属性对应的值
	 * 
	 * @param message
	 * @param param
	 * @return
	 */
	private String getMessageParamValue(Map message, String param) {
		if (message != null && !message.isEmpty()) {
			if (message.containsKey(param)) {
				return String.valueOf(message.get(param));
			}
			for (Object entry : message.values()) {
				if (entry instanceof Map) {
					String result = getMessageParamValue((Map) entry, param);
					if (result != null) {
						return result;
					}
				}
			}
		}

		return null;
	}
	
	/**
	 * 是否包含在白名单中
	 * @param value
	 * @param param
	 * @return
	 */
	private boolean containsWhitelist(String value, String param) {
		Set<String> whitelist = agentCache.getWhitelist(getConsumerId(),getApplicationId());
		return whitelist.contains(param+":"+value);
	}
}
