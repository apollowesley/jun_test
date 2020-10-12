package com.jeasy.base.model;

import java.io.Serializable;

import lombok.Data;

/**
 * BaseModel
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
@Data
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
}