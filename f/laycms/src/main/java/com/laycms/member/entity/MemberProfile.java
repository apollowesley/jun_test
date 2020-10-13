package com.laycms.member.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.laycms.base.entity.BaseEntity;

/** 
* @author 作者 zbb: 
* @version 创建时间：2016年6月23日 下午2:24:37 
* 类说明 
*/

@Entity
@Table(name="member_profile")
public class MemberProfile extends BaseEntity {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	//用户Id
	private Integer memberId;
	
	private String avatar;
	private String realname; // 真实姓名
	
	private String qq;  //QQ号码
	private Integer sex;  // 0 女   1 男
	private String univName;  //本科院校
	private String univMajor; //本科专业
	
	private String wishUnivName; //报考院校
	private String wishMajor;   //报考专业
	private Integer examYear;   //考研年份
	private String address;   //联系地址
	
	//收货信息
	private String consignee;   //收货人
	private String consigneeAddress;   //收货地址
	private String consigneeMobile;   //联系方式

	private String regFrom;
	
	public String getRegFrom() {
		return regFrom;
	}
	public void setRegFrom(String regFrom) {
		this.regFrom = regFrom;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public String getRealname() {
		return realname;
	}
	public void setRealname(String realname) {
		this.realname = realname;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public String getUnivName() {
		return univName;
	}
	public void setUnivName(String univName) {
		this.univName = univName;
	}
	public String getUnivMajor() {
		return univMajor;
	}
	public void setUnivMajor(String univMajor) {
		this.univMajor = univMajor;
	}
	public String getWishUnivName() {
		return wishUnivName;
	}
	public void setWishUnivName(String wishUnivName) {
		this.wishUnivName = wishUnivName;
	}
	public String getWishMajor() {
		return wishMajor;
	}
	public void setWishMajor(String wishMajor) {
		this.wishMajor = wishMajor;
	}
	public Integer getExamYear() {
		return examYear;
	}
	public void setExamYear(Integer examYear) {
		this.examYear = examYear;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getConsignee() {
		return consignee;
	}
	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}
	public String getConsigneeAddress() {
		return consigneeAddress;
	}
	public void setConsigneeAddress(String consigneeAddress) {
		this.consigneeAddress = consigneeAddress;
	}
	public String getConsigneeMobile() {
		return consigneeMobile;
	}
	public void setConsigneeMobile(String consigneeMobile) {
		this.consigneeMobile = consigneeMobile;
	}
	
	
	
}
