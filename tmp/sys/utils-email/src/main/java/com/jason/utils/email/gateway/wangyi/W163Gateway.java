package com.jason.utils.email.gateway.wangyi;

import com.jason.utils.email.Gateway;
/**
 * 163邮箱
 * @author jason
 *
 */
public class W163Gateway extends Gateway{

	public W163Gateway() {
		super("smtp.163.com");
	}

	@Override
	public boolean isAdaptation(String email) {
		if(isEmail(email)){
			return isPattern(email, "([a-z0-9A-Z]+[-|\\.|\\_]?)+[a-z0-9A-Z]@(163.com)");
		}
		return false;
	}

}