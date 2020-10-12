package com.lijian.devutils.bean;

import java.io.Serializable;

public class ResponseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String rescode;
	private String msg;
	private String submsg;
	private String tohref;
	private Object data;
	
	public void setRescode(String rescode) {
		this.rescode = rescode;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public void setSubmsg(String submsg) {
		this.submsg = submsg;
	}
	public void setTohref(String tohref) {
		this.tohref = tohref;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getRescode() {
		return rescode;
	}
	public String getMsg() {
		return msg;
	}
	public String getSubmsg() {
		return submsg;
	}
	public String getTohref() {
		return tohref;
	}
	public Object getData() {
		return data;
	}
	public ResponseBean() {
		super();
	}
	public ResponseBean(String rescode, String msg, String submsg,
			String tohref, Object data) {
		super();
		this.rescode = rescode;
		this.msg = msg;
		this.submsg = submsg;
		this.tohref = tohref;
		this.data = data;
	}
	@Override
	public String toString() {
		return "ResponseBean [rescode=" + rescode + ", msg=" + msg
				+ ", submsg=" + submsg + ", tohref=" + tohref + ", data="
				+ data + "]";
	}
	
	
}
