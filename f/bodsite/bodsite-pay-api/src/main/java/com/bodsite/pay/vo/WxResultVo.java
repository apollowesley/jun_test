package com.bodsite.pay.vo;

import java.io.Serializable;

/**
 * 
* 微信返回结果类
* @author bod
* @date 2017年1月7日 下午3:56:59 
*
 */
public class WxResultVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String appid;//应用ID
	private String partnerid;//商户号
	private String prepayid;//预支付交易会话ID
	private String packages;//扩展字段
	private String noncestr;//随机字符串	
	private String timestamp;//时间戳
	private String sign;//签名
	
	private String codeUrl;//二维码链接
	//jsapi需要
	private String signType;//加密方式
	public String getAppid() {
		return appid;
	}
	public void setAppid(String appid) {
		this.appid = appid;
	}
	public String getPartnerid() {
		return partnerid;
	}
	public void setPartnerid(String partnerid) {
		this.partnerid = partnerid;
	}
	public String getPrepayid() {
		return prepayid;
	}
	public void setPrepayid(String prepayid) {
		this.prepayid = prepayid;
	}
	public String getPackages() {
		return packages;
	}
	public void setPackages(String packages) {
		this.packages = packages;
	}
	public String getNoncestr() {
		return noncestr;
	}
	public void setNoncestr(String noncestr) {
		this.noncestr = noncestr;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getCodeUrl() {
		return codeUrl;
	}
	public void setCodeUrl(String codeUrl) {
		this.codeUrl = codeUrl;
	}
	public String getSignType() {
		return signType;
	}
	public void setSignType(String signType) {
		this.signType = signType;
	}
	@Override
	public String toString() {
		return "WxResultVo [appid=" + appid + ", partnerid=" + partnerid + ", prepayid=" + prepayid + ", packages="
				+ packages + ", noncestr=" + noncestr + ", timestamp=" + timestamp + ", sign=" + sign + ", codeUrl="
				+ codeUrl + ", signType=" + signType + "]";
	}
	
}
