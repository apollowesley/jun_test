package com.jeasy.blog.model;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * @author taomk
 * @version 1.0
 * @since 2015/06/22 16:08
 */
@Data
public class Blog implements Serializable {

	private static final long serialVersionUID = -990334519496260591L;

	/**
	 * 
	 */
	private long id;

	/**
	 * 
	 */
	private String title;

	/**
	 * 
	 */
	private String content;

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