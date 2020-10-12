package site.zhouinfo.mysql.bean;

/**
 * Crawler
 * Author:      zhou
 * Create Date：2016-02-02 22:41
 */
public class City {
	protected String Id;
	protected String area;
	protected City parent;
	protected String parentCode;
	protected String cityCode;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public City getParent() {
		return parent;
	}

	public void setParent(City parent) {
		this.parent = parent;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}
}
