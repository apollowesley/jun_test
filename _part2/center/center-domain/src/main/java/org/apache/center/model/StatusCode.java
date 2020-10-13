package org.apache.center.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 *
 * 数据库状态码（10=待审核等等）
 *
 */
@TableName(value="status_code",resultMap="BaseResultMap")
public class StatusCode implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主健 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 组编号 */
	@TableField(value = "group_num")
	private String groupNum;

	/** 组名称 */
	@TableField(value = "group_name")
	private String groupName;

	/** 节点编号 */
	@TableField(value = "node_num")
	private String nodeNum;

	/** 节点key */
	@TableField(value = "node_key")
	private String nodeKey;

	/** 节点value */
	@TableField(value = "node_value")
	private String nodeValue;

	/** 排序号 */
	private Integer sequence;

	/** 是否使用（Y=是，N=否） */
	@TableField(value = "is_use")
	private String isUse;

	/** 创建时间 */
	@TableField(value = "create_time")
	private Date createTime;

	/** 创建人id */
	@TableField(value = "create_user_id")
	private Long createUserId;

	/** 更新时间 */
	@TableField(value = "update_time")
	private Date updateTime;

	/** 更新人id */
	@TableField(value = "update_user_id")
	private Long updateUserId;

	/** 是否删除 */
	@TableField(value = "is_delete")
	private String isDelete;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getGroupNum() {
		return this.groupNum;
	}

	public void setGroupNum(String groupNum) {
		this.groupNum = groupNum;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getNodeNum() {
		return this.nodeNum;
	}

	public void setNodeNum(String nodeNum) {
		this.nodeNum = nodeNum;
	}

	public String getNodeKey() {
		return this.nodeKey;
	}

	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}

	public String getNodeValue() {
		return this.nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getIsUse() {
		return this.isUse;
	}

	public void setIsUse(String isUse) {
		this.isUse = isUse;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Long getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getIsDelete() {
		return this.isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

}
