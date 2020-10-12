package com.foo.common.base.reflect;

import java.util.Date;

import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class ReflectPojoParent {

	private String myStr;
	private int myInt;
	private Date myDate;
	private transient int myTransientInt;

	public String getMyStr() {
		return myStr;
	}

	public void setMyStr(String myStr) {
		this.myStr = myStr;
	}

	public int getMyInt() {
		return myInt;
	}

	public void setMyInt(int myInt) {
		this.myInt = myInt;
	}

	public Date getMyDate() {
		return myDate;
	}

	public void setMyDate(Date myDate) {
		this.myDate = myDate;
	}

	public int getMyTransientInt() {
		return myTransientInt;
	}

	public void setMyTransientInt(int myTransientInt) {
		this.myTransientInt = myTransientInt;
	}

}
