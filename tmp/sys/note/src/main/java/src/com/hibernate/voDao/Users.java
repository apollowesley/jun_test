package com.hibernate.voDao;

/**
 * users entity. @author MyEclipse Persistence Tools
 */

public class Users implements java.io.Serializable {

	// Fields

	private Integer uid;
	private String loginid;
	private String username;
	private String password;
	private String realname;
	private String identityCardNumber;
	private String email;
	private boolean avatarstatus;
	private boolean adminid;
	private String telephone;
	private String identification;
	private String jPushId;
	private String qqOpenId;
	private String sinaWeiboAccessToken;

	// Constructors

	/** default constructor */
	public Users() {
	}

	/** full constructor */
	public Users(Integer uid, String loginid, String username, String password, String realname, String identityCardNumber, String email, boolean avatarstatus, boolean adminid,
			String telephone, String identification, String jPushId, String qqOpenId, String sinaWeiboAccessToken) {
		super();
		this.uid = uid;
		this.loginid = loginid;
		this.username = username;
		this.password = password;
		this.realname = realname;
		this.identityCardNumber = identityCardNumber;
		this.email = email;
		this.avatarstatus = avatarstatus;
		this.adminid = adminid;
		this.telephone = telephone;
		this.identification = identification;
		this.jPushId = jPushId;
		this.qqOpenId = qqOpenId;
		this.sinaWeiboAccessToken = sinaWeiboAccessToken;
	}

	// Property accessors

	public Integer getUid() {
		return uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	public String getLoginid() {
		return loginid;
	}

	public void setLoginid(String loginid) {
		this.loginid = loginid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getIdentityCardNumber() {
		return identityCardNumber;
	}

	public void setIdentityCardNumber(String identityCardNumber) {
		this.identityCardNumber = identityCardNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean getAvatarstatus() {
		return avatarstatus;
	}

	public void setAvatarstatus(boolean avatarstatus) {
		this.avatarstatus = avatarstatus;
	}

	public boolean getAdminid() {
		return adminid;
	}

	public void setAdminid(boolean adminid) {
		this.adminid = adminid;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getIdentification() {
		return identification;
	}

	public void setIdentification(String identification) {
		this.identification = identification;
	}

	public String getjPushId() {
		return jPushId;
	}

	public void setjPushId(String jPushId) {
		this.jPushId = jPushId;
	}

	public String getQqOpenId() {
		return qqOpenId;
	}

	public void setQqOpenId(String qqOpenId) {
		this.qqOpenId = qqOpenId;
	}

	public String getSinaWeiboAccessToken() {
		return sinaWeiboAccessToken;
	}

	public void setSinaWeiboAccessToken(String sinaWeiboAccessToken) {
		this.sinaWeiboAccessToken = sinaWeiboAccessToken;
	}

}