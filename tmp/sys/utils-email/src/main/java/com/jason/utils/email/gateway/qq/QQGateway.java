package com.jason.utils.email.gateway.qq;

import com.jason.utils.email.Gateway;

/**
 * qq邮箱</br>
 * <h2>qq邮箱要求必须开启SSL登录</h2>
 * <h3>qq邮箱必须进行设置：</h3>
 * <ol>
 * <li>设置独立密码</li>
 * <li>开启POP3/SMTP服务或者IMAP/SMTP服务</li>
 * <li>account中设置的密码是开启SMTP服务的授权码 </li>
 * </ol>
 * <h3>必须按照上面的顺序来</h3>
 * 
 * @author jason
 */
public class QQGateway extends Gateway {

	public QQGateway() {
		super("smtp.qq.com");
		//465用不了
		setPort("587");
	}

	@Override
	public boolean isAdaptation(String email) {
		if(isEmail(email)){
			return isPattern(email, "(([0-9]{6,}@qq.com)|(([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@foxmail.com))");
		}
		return false;
	}
   
    @Override
	public String resolveAccount(String email) {
		return email;
	}
}