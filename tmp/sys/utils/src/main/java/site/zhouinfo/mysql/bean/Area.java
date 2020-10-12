package site.zhouinfo.mysql.bean;

import java.util.Date;

/**
 * Crawler
 * Author:      zhou
 * Create Date：2016-02-02 22:42
 */
public class Area {

	private String id;
	private String parent;        // 父级编号
	private String parentIds;     // 所有父级编号
	private String code;            // 区域编码
	private String name;            // 区域名称
	private Integer sort;            // 排序
	private int type;            // 区域类型（1：国家；2：省份、直辖市；3：地市；4：区县 5:镇 6：街道，居委）
	private String remarks;        // 备注
	private String createBy;        // 创建者
	private Date createDate;        // 创建日期
	private String updateBy;        // 更新者
	private Date updateDate;        // 更新日期
	private String delFlag;        // 删除标记（0：正常；1：删除；2：审核）

	public Area() {
		this.createBy="1";
		this.updateBy="1";
		this.createDate=new Date();
		this.updateDate=new Date();
		this.delFlag="0";
		this.code="";
		this.remarks="";
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getParentIds() {
		return parentIds;
	}

	public void setParentIds(String parentIds) {
		this.parentIds = parentIds;
	}



	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}
}
