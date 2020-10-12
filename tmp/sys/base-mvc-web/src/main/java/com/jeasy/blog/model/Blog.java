package com.jeasy.blog.model;


import com.jeasy.base.model.BaseModel;

import lombok.Data;

/**
 * 
 * @author taomk
 * @version 1.0
 * @since 2015/06/21 14:52
 */
@Data
public class Blog extends BaseModel {

	private static final long serialVersionUID = 5409185459234711691L;

	/**
	 * 
	 */
	private String title;
	/**
	 * 
	 */
	private String content;
}