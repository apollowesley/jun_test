package com.foo.common.base.utils;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import ch.qos.logback.classic.Level;

import com.google.common.collect.Lists;

public class LogbackHelper {

	final static ch.qos.logback.classic.Logger classiclogger = (ch.qos.logback.classic.Logger) LoggerFactory
			.getLogger("classic");
	final static Logger commonlogger = LoggerFactory
			.getLogger(Logger.ROOT_LOGGER_NAME);
	final static Logger inheritLogger = LoggerFactory
			.getLogger(LogbackHelper.class);

	public static void main(String[] args) throws InterruptedException {

		List<String> myList = Lists.newArrayList("123", "456");
		commonlogger.info("list print one:{},two:{},tesr:{}", myList.toArray());

		classiclogger.info("{}", "classiclogger");
		commonlogger.info("{}", "common");

		classiclogger.setLevel(Level.OFF);
		commonlogger.error("you should never see this statement.");

		inheritLogger
				.warn("this is the inherit log from com.foo.common.base.utils");

		Marker severeMarker = MarkerFactory.getMarker("NOTIFY_ADMIN");
		Logger email_Logger = LoggerFactory.getLogger("email_Logger");
		email_Logger.error(severeMarker, "see it?");
		Thread.sleep(60000000);

	}

}
