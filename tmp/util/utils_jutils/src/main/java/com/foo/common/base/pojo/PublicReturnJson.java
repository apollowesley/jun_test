package com.foo.common.base.pojo;

import com.google.gson.annotations.Expose;

public class PublicReturnJson {
	@Expose
	private int ret;
	@Expose
	private String msg;

	public int getRet() {
		return ret;
	}

	public void setRet(int ret) {
		this.ret = ret;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
