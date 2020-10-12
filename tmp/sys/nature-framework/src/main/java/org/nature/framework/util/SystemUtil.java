package org.nature.framework.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemUtil {
	 private static final Logger LOGGER = LoggerFactory.getLogger(SystemUtil.class);
	public static String getHostAddress(){
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			LOGGER.error("getHostAddress error");
		}
		return address.getHostAddress();
	}
	
	public static String getHostName(){
		InetAddress address = null;
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			LOGGER.error("getHostName error");
		}
		return address.getHostName();
	}
	
}
