package com.xlz.engine.rocketmq.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.rocketmq.common.filter.MessageFilter;
import com.alibaba.rocketmq.common.message.MessageExt;

public class GrayMessageFilterImpl extends AbstractMessageFilterImpl implements MessageFilter{

	private final static Logger logger = LoggerFactory.getLogger(GrayMessageFilterImpl.class);
	public static String consumerId = "CONSUMER_ID";
	public static String applicationId = "APP_ID";
	
	@Override
	protected boolean filterResult() {
		return true;
	}

	@Override
	protected String getConsumerId() {
		return consumerId;
	}

	@Override
	protected String getApplicationId() {
		return applicationId;
	}
	
	@Override
	public boolean match(MessageExt msg) {
		return myMatch(msg);
	}
}
