package com.gitee.app.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name = "t_user")
public class TUser {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String username;
	private Integer state;
	private Boolean isdel;
	private String remark;
	private Date addTime;
	private BigDecimal money;
	private Float leftMoney;

	@Transient
	private String city;
	private transient String address;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return this.id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUsername() {
		return this.username;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getState() {
		return this.state;
	}

	public void setIsdel(Boolean isdel) {
		this.isdel = isdel;
	}

	public Boolean getIsdel() {
		return this.isdel;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}

	public Date getAddTime() {
		return this.addTime;
	}

	public void setMoney(BigDecimal money) {
		this.money = money;
	}

	public BigDecimal getMoney() {
		return this.money;
	}

	public void setLeftMoney(Float leftMoney) {
		this.leftMoney = leftMoney;
	}

	public Float getLeftMoney() {
		return this.leftMoney;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("TUser [");
		sb.append("id=").append(id);
		sb.append(", ");
		sb.append("username=").append(username);
		sb.append(", ");
		sb.append("state=").append(state);
		sb.append(", ");
		sb.append("isdel=").append(isdel);
		sb.append(", ");
		sb.append("remark=").append(remark);
		sb.append(", ");
		sb.append("addTime=").append(addTime);
		sb.append(", ");
		sb.append("money=").append(money);
		sb.append(", ");
		sb.append("leftMoney=").append(leftMoney);
		sb.append("]");
		return sb.toString();
	}

}