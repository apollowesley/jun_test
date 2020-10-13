package com.myapp.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.myapp.entity.type.UserState;

import net.oschina.durcframework.easymybatis.annotation.LogicDelete;

@Table(name = "t_user")
public class TUser {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "username")
	private String username;
	@Column(name = "state")
	private UserState state;
	@Column(name = "isdel")
	@LogicDelete
	private Byte isdel;
	@Column(name = "remark")
	private String remark;
	@Column(name = "add_time")
	private Date addTime;
	@Column(name = "money")
	private BigDecimal money;
	@Column(name = "left_money")
	private Float leftMoney;

	@Transient
	private String city;
	@Transient
	private String address;
	
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

	public void setState(UserState state) {
		this.state = state;
	}

	public UserState getState() {
		return this.state;
	}

	public void setIsdel(Byte isdel) {
		this.isdel = isdel;
	}

	public Byte getIsdel() {
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
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TUser other = (TUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TUser [id=" + id + ", username=" + username + ", state=" + state + ", isdel=" + isdel + ", remark="
				+ remark + ", addTime=" + addTime + ", money=" + money + ", leftMoney=" + leftMoney + ", city=" + city
				+ ", address=" + address + "]";
	}
}