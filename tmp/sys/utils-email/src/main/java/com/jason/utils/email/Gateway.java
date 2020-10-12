package com.jason.utils.email;

import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jason.utils.email.gateway.GatewayGroup;

public abstract class Gateway{
    private String host;
	
	private String port="25";
	
	private boolean auth=true;
	
	private String text;
	
	protected Gateway(String host) {
		this(host,null);
		this.text=getClass().getName();
	}
	
	private Gateway(String host,String text) {
		this.host=host;
		this.text=text;
	}

	public String getHost() {
		return host;
	}

	public String getPort() {
		return port;
	}

	public boolean isAuth() {
		return auth;
	}

	public String getText() {
		return text;
	}
	
	public void setText(String text) {
		this.text = text;
	}
	
	public void setHost(String host) {
		this.host = host;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public Properties getProperties(){
		Properties properties=new Properties();
		properties.put("mail.smtp.host", this.host);
		properties.put("mail.smtp.port", this.port);
		properties.put("mail.smtp.auth", this.auth ? "true" : "false");
        return properties;
	}
	
	/**
	 * 是否适配当前网关
	 * @return
	 */
	public abstract boolean isAdaptation(String email);
	
	/**
	 * 通过email解析出登录账户
	 * @param email
	 * @return
	 */
	public String resolveAccount(String email) {
		if(email==null || !isAdaptation(email)){
			return null;
		}
		String str[]=email.split("@");
		if(str!=null && str.length==2){
			return str[0];
		}
		return null;
	}
	
	public static Gateway mailSuffixAdaptation(String email) {
		Gateway gateway=null;
		for(Gateway g: GatewayGroup.GATEWAYS){
			if(g.isAdaptation(email)){
				gateway=g;
				break;
			}
		}
		return gateway;
	}
	
	protected boolean isEmail(String email){
		if(email==null){
			return false;
		}
		String regex = "^([a-z0-9A-Z]+[-|\\.|\\_]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
		return isPattern(email, regex);
	}
	
	/**
	 * 判断字符串是否匹配正则表达式
	 * @param string
	 * @param regex
	 * @return
	 */
	protected boolean isPattern(String string,String regex){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(string);
		return matcher.matches();
	}
}