package org.apache.center.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.apache.center.model.enums.resources.Type;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;

/**
 *
 * 资源表
 *
 */
@TableName(value="resources",resultMap="BaseResultMap")
public class Resources implements Serializable {

	@TableField(exist = false)
	private static final long serialVersionUID = 1L;

	/** 主健 */
	@TableId(type = IdType.AUTO)
	private Long id;

	/** 名称 */
	private String name;

	/** 链接URL */
	@TableField(value = "link_url")
	private String linkUrl;

	/** 描述 */
	private String description;

	/** 父id */
	@TableField(value = "parent_id")
	private Long parentId;

	/** 排序号 */
	private Integer sequence;

	/** 权限码 */
	@TableField(value = "auth_code")
	private String authCode;

	/** 类型（module=模块，method=方法） */
	private String type;

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

	public String getLinkUrl() {
		return this.linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getParentId() {
		return this.parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public Integer getSequence() {
		return this.sequence;
	}

	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	public String getAuthCode() {
		return this.authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
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

	private String state;

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getTypeText() {
		return Type.getInstance().getText(type);
	}
	
	private List<Resources> children;

	public List<Resources> getChildren() {
		return children;
	}

	public void setChildren(List<Resources> children) {
		this.children = children;
	}
	
}
