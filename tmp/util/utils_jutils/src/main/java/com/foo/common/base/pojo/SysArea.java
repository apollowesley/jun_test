package com.foo.common.base.pojo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 系统地域
 */
@Entity
@Table(name = "SYS_AREA")
public class SysArea {

	@Id
	@Column(name = "id", length = 36)
	@javax.persistence.GeneratedValue(generator = "system-uuid")
	@org.hibernate.annotations.GenericGenerator(name = "system-uuid",
			strategy = "uuid2")
	@Expose
	@SerializedName("id")
	private String id;

	@Column(name = "AREA_NAME", length = 100, nullable = false)
	@Expose
	@SerializedName("name")
	private String areaName = "";// 字典中文名称

	@Column(name = "INTERFACE_ID", length = 100)
	private String interfaceId = "";// 其他系统的字典id

	@Column(name = "AREA_ID", length = 100, nullable = false)
	@Expose
	@SerializedName("dictId")
	private String areaId = "";// 字典代码,超级节点：{dictId:1,parentDictId:0},第一个节点：{dictId:101,parentDictId:1}，以此类推

	@Column(name = "PARENT_AREA_ID", length = 100, nullable = false)
	@Expose
	@SerializedName("pId")
	private String parentAreaId = "";// 父节点字典id

	@Column(name = "LEAF", nullable = false)
	private Integer leaf;// 是否是叶子节点，，0是父节点，1是子节点

	@Column(name = "ORDER_NO", nullable = false)
	private Integer orderNo;// 排序号

	@Column(name = "STATE", nullable = false)
	@Expose
	private Integer state;// 字典是否可用，0是不可用，1是可用。

	// Ztree token start
	@Expose
	@SerializedName("open")
	// @Transient
	private boolean treeOpen = true;// 默认打开

	@Expose
	@Transient
	private String title = "";// 前台匹配显示url，不需要持久化

	@Column(name = "TREE_LEVEL")
	private int treeLevel;

	@Column(name = "TYPE", length = 5)
	@Expose
	@SerializedName("type")
	private String type = "area";

	@Column(name = "CREATE_TIME")
	private Date createTime = new Date();// 保存或者刷新的时间

	@Transient
	@Expose
	@SerializedName("isParent")
	private boolean parent = false;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getInterfaceId() {
		return interfaceId;
	}

	public void setInterfaceId(String interfaceId) {
		this.interfaceId = interfaceId;
	}
	public Integer getLeaf() {
		return leaf;
	}

	public void setLeaf(Integer leaf) {
		this.leaf = leaf;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public boolean isTreeOpen() {
		return treeOpen;
	}

	public void setTreeOpen(boolean treeOpen) {
		this.treeOpen = treeOpen;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getTreeLevel() {
		return treeLevel;
	}

	public void setTreeLevel(int treeLevel) {
		this.treeLevel = treeLevel;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public boolean isParent() {
		return parent;
	}

	public void setParent(boolean parent) {
		this.parent = parent;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public String getParentAreaId() {
		return parentAreaId;
	}

	public void setParentAreaId(String parentAreaId) {
		this.parentAreaId = parentAreaId;
	}

}
