package com.managementsystem.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;



@Entity
@Table(name="resources")
public class Resources extends BaseEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String text;// 资源名称
	private String url;// 访问地址
	private String sign;//资源标识
	private Boolean isSystem = false;// 是否为系统内置资源
	private Boolean isEnable;//是否启用
	private String description;// 描述
	private Integer orderList;// 排序
	private Integer typ;//资源类型 1：模块 2:子模块 3:视图资源4:操作
	private Resources parent;//上级分类
	private Set<Resources> children;// 下级分类
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Boolean getIsSystem() {
		return isSystem;
	}
	public void setIsSystem(Boolean isSystem) {
		this.isSystem = isSystem;
	}
	public Boolean getIsEnable() {
		return isEnable;
	}
	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getOrderList() {
		return orderList;
	}
	public void setOrderList(Integer orderList) {
		this.orderList = orderList;
	}
	public Integer getTyp() {
		return typ;
	}
	public void setTyp(Integer typ) {
		this.typ = typ;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")  
	public Resources getParent() {
		return parent;
	}
	public void setParent(Resources parent) {
		this.parent = parent;
	}
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", fetch = FetchType.EAGER)  
	public Set<Resources> getChildren() {
		return children;
	}
	public void setChildren(Set<Resources> children) {
		this.children = children;
	}
	
}
