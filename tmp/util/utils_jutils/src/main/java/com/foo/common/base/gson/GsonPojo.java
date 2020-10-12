package com.foo.common.base.gson;

import java.util.Date;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.Since;

public class GsonPojo {

	@Expose
	@Since(1.0)
	private String myStr1;
	@Since(2.0)
	@GsonAnnotation
	private int myInt1;
	private Date myDate1;
	private transient int myTransientInt1;

	public String getMyStr1() {
		return myStr1;
	}

	public void setMyStr1(String myStr1) {
		this.myStr1 = myStr1;
	}

	public int getMyInt1() {
		return myInt1;
	}

	public void setMyInt1(int myInt1) {
		this.myInt1 = myInt1;
	}

	public Date getMyDate1() {
		return myDate1;
	}

	public void setMyDate1(Date myDate1) {
		this.myDate1 = myDate1;
	}

	public int getMyTransientInt1() {
		return myTransientInt1;
	}

	public void setMyTransientInt1(int myTransientInt1) {
		this.myTransientInt1 = myTransientInt1;
	}

}
