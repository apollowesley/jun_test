package org.coody.czone.web.blog.domain;

import org.coody.framework.jdbc.entity.DBModel;
/**
 * 博主信息表
 * @author Coody
 * @date 2018年8月15日
 */
@SuppressWarnings("serial")
public class AuthorInfo extends DBModel{

	/**
	 * 用户ID （UUID）
	 */
	private String userId;
	/**
	 * 用户昵称（网名）
	 */
	private String nickName;
	/**
	 * 用户职业
	 */
	private String occupation;
	/**
	 * 籍贯
	 */
	private String address;
	/**
	 * 微信二维码
	 */
	private String wxQrCode;
	/**
	 * 头像
	 */
	private String head;
	/**
	 * 个性签名
	 */
	private String sign;
	
	/**
	 * 联系邮箱
	 */
	private String email;
	
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getOccupation() {
		return occupation;
	}
	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}
	public String getWxQrCode() {
		return wxQrCode;
	}
	public void setWxQrCode(String wxQrCode) {
		this.wxQrCode = wxQrCode;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	
	

}
