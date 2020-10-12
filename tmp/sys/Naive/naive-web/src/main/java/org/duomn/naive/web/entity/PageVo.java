package org.duomn.naive.web.entity;

/**
 * 接收Flexigrid的VO
 * @author Hu Qiang
 *
 */
public class PageVo {
	/** 选择的查询字段的名称 */
	private String qtype; 
	/** 查询名称的内容 */
	private String query; 
	/** 排序字段的名称 */
	private String sortname; 
	/** 升序或者降序 */
	private String sortorder = "asc";
	/** 每页的条数 */
	private int rp = 20; 
	/** 总的记录条数 */
	private int page = 1;
	
	public String getQtype() {
		return qtype;
	}
	public void setQtype(String qtype) {
		this.qtype = qtype;
	}
	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}
	public String getSortname() {
		return sortname;
	}
	public void setSortname(String sortname) {
		this.sortname = sortname;
	}
	public String getSortorder() {
		return sortorder;
	}
	public void setSortorder(String sortorder) {
		this.sortorder = sortorder;
	}
	public int getRp() {
		return rp;
	}
	public void setRp(int rp) {
		this.rp = rp;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	
}
