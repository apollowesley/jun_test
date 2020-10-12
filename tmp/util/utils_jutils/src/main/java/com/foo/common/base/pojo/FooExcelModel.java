package com.foo.common.base.pojo;

import java.util.Date;

public class FooExcelModel {
	private String x0;
	private String x1;
	private Date x2;
	private String x3;
	private String x4;

	@Override
	public String toString() {
		return "x0:" + getX0() + "x1:" + getX1() + "x2:" + getX2() + "x3:"
				+ getX3() + "x4:" + getX4();
	}

	public String getX0() {
		return x0;
	}

	public void setX0(String x0) {
		this.x0 = x0;
	}

	public String getX1() {
		return x1;
	}

	public void setX1(String x1) {
		this.x1 = x1;
	}

	public Date getX2() {
		return x2;
	}

	public void setX2(Date x2) {
		this.x2 = x2;
	}

	public String getX3() {
		return x3;
	}

	public void setX3(String x3) {
		this.x3 = x3;
	}

	public String getX4() {
		return x4;
	}

	public void setX4(String x4) {
		this.x4 = x4;
	}
}
