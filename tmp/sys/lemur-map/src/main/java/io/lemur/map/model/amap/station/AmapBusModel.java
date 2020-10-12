package io.lemur.map.model.amap.station;

import java.io.Serializable;

public class AmapBusModel implements Serializable{

	
	private static final long serialVersionUID = -472826739480075611L;
	/**
	 * 公交站台名字
	 */
	private String name;
	/**
	 * 类型
	 */
	private String srctype;
	/**
	 * 经度
	 */
	private String x;
	/**
	 * 纬度
	 */
	private String y;
	
	private String address;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSrctype() {
		return srctype;
	}
	public void setSrctype(String srctype) {
		this.srctype = srctype;
	}
	public String getX() {
		return x;
	}
	public void setX(String x) {
		this.x = x;
	}
	public String getY() {
		return y;
	}
	public void setY(String y) {
		this.y = y;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
}
