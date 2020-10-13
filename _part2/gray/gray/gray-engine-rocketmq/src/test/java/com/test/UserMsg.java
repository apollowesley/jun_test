package com.test;

import java.io.Serializable;

public class UserMsg  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id ;
	private String msg;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public UserMsg(Integer id, String msg) {
		super();
		this.id = id;
		this.msg = msg;
	}
	
	
}
