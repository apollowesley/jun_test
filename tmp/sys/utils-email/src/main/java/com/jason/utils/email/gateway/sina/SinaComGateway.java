package com.jason.utils.email.gateway.sina;

import com.jason.utils.email.Gateway;

/**
 * 新浪com网关
 * @author jason
 *
 */
public class SinaComGateway extends Gateway {
	public SinaComGateway() {
		super("smtp.sina.com");
	}

	@Override
	public boolean isAdaptation(String email) {
		if(isEmail(email)){
			return isPattern(email, "([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@(sina.com|vip.sina.com)");
		}
		return false;
	}
}