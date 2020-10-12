package com.siweifu.vo;

import com.siweifu.utils.JsonUtils;

/**
 * 功能描述: 封装ajax返回
 * @title AjaxResult.java
 * @description 
 * @company 北京思维夫网络科技有限公司
 * @url http://www.meitimao.com
 * @author 卢春梦  
 * @version 1.0
 * @created 2015年4月9日下午6:55:53
 */
public class AjaxResult {

	// 标记成功失败，默认0：成功，1：失败、用于alert，2：失败、用于confirm
	private int code = 0;

	// 返回的中文消息
	private String message;

	// 成功时携带的对象
	private Object data;

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public Object getData() {
		return data;
	}

	// 校验错误
	public boolean hasError() {
		return this.code != 0;
	}

	// 添加错误，用于alertError
	public AjaxResult addError(String message) {
		this.message = message;
		this.code = 1;
		return this;
	}

	/**
	 * 用于Confirm的错误信息
	 * @param addConfirmError
	 * @return AjaxResult
	 */
	public AjaxResult addConfirmError(String message) {
		this.message = message;
		this.code = 2;
		return this;
	}

	/**
	 * 成功时携带的数据
	 * @param data
	 * @return AjaxResult
	 */
	public AjaxResult addSuccess(Object data) {
		this.data = data;
		return this;
	}

	@Override
	public String toString() {
		return JsonUtils.toJson(this);
	}

}
