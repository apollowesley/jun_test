package com.jason.utils.email.gateway.sina;

import com.jason.utils.email.Gateway;

/**
 * 新浪cn网关
 * @author jason
 *
 */
public class SinaCnGateway extends Gateway {
	public SinaCnGateway() {
		super("smtp.sina.cn");
	}

	@Override
	public boolean isAdaptation(String email) {
		if(isEmail(email)){
			return isPattern(email, "([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@(sina.cn)");
		}
		return false;
	}

}