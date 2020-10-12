package com.king.base.model;

import java.io.Serializable;

/**
 * BaseModel
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class BaseModel implements Serializable {

	private static final long serialVersionUID = 5409185459234711691L;

	/**
	 * 主键
	 */
	private long id;
	/**
	 * 是否删除
	 */
	private int isDel;
	/**
	 * 创建时间
	 */
	private long createAt;
	/**
	 * 创建者ID
	 */
	private long createBy;
	/**
	 * 创建者名称
	 */
	private String createName;
	/**
	 * 更新时间
	 */
	private long updateAt;
	/**
	 * 更新者ID
	 */
	private long updateBy;
	/**
	 * 更新者名称
	 */
	private String updateName;

	public long getId() {
		return id;
	}
    public void setId(long id) {
    	this.id = id;
    }

	public int getIsDel() {
		return isDel;
	}
    public void setIsDel(int isDel) {
    	this.isDel = isDel;
    }

	public long getCreateAt() {
		return createAt;
	}
    public void setCreateAt(long createAt) {
    	this.createAt = createAt;
    }

	public long getCreateBy() {
		return createBy;
	}
    public void setCreateBy(long createBy) {
    	this.createBy = createBy;
    }

	public String getCreateName() {
		return createName;
	}
    public void setCreateName(String createName) {
    	this.createName = createName;
    }

	public long getUpdateAt() {
		return updateAt;
	}
    public void setUpdateAt(long updateAt) {
    	this.updateAt = updateAt;
    }

	public long getUpdateBy() {
		return updateBy;
	}
    public void setUpdateBy(long updateBy) {
    	this.updateBy = updateBy;
    }

	public String getUpdateName() {
		return updateName;
	}
    public void setUpdateName(String updateName) {
    	this.updateName = updateName;
    }
}