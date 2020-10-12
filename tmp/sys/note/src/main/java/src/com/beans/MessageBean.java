/**
 * 封闭用户消息的类，所有用户返回的消息都使用此类
 * @auther wuwang
 * @createTime 2014-11-29 上午12:40:46
 */
package com.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author peaches
 */
public class MessageBean implements Serializable {
	/** 消息标题 **/
	private String title;
	/** 消息概要内容 **/
	private String message;
	/** 读消息时发生的动作，通常是标记为已读状态 **/
	private String action;
	/** 如果action是个按钮，这里就是名称 **/
	private String actionName;
	/** 消息时间 **/
	private Date date;
	/** 图片内容 **/
	private String img;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

}
