package org.apache.center.model;

import java.io.Serializable;
import java.util.Date;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 *
 * 参数表
 *
 */
@TableName(value="param",resultMap="BaseResultMap")
public class Param implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主健 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 名称 */
	private String name;

	/** 参数key */
	@TableField(value = "param_key")
	private String paramKey;

	/** 参数value */
	@TableField(value = "param_value")
	private String paramValue;

	/** 描述 */
	private String description;

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

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getParamKey() {
		return this.paramKey;
	}

	public void setParamKey(String paramKey) {
		this.paramKey = paramKey;
	}

	public String getParamValue() {
		return this.paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
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
