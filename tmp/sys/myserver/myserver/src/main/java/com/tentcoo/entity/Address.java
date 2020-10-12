package com.tentcoo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="tb_login")
public class Address extends IdEntity{
	@Column(name = "f_devices")
	String devices;
	@Column(name = "f_time")
	String time;
	@Column(name = "f_ip")
	String userIp;
	public Address() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Address(String devices, String time, String userIp) {
		super();
		this.devices = devices;
		this.time = time;
		this.userIp = userIp;
	}
	public String getDevices() {
		return devices;
	}
	public void setDevices(String devices) {
		this.devices = devices;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
}
