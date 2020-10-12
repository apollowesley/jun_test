package com.jfast.web.common.core.utils;
/**
 * 为方便处理ajax请求
 * @author zengjintao
 * @version 1.0
 * @create_at 2017年6月28日上午9:28:04
 */
public class ResultCode {

	public static final int UN_AUTH_ERROR_CODE = 401; //用户未认证
	public static final int TOKEN_INVALID = 403; //token 失效
	public static final int NO_URL_PERMISSION = 406; //没有url 访问权限
	public static final int SUCCESS = 1; //响应成功
	public static final int FAIL = 0; //响应失败
	
	public int code = SUCCESS;
	public String message = "操作成功";

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public ResultCode(int code, String message) {
		this.code = code;
		this.message = message;
	}
	
	public ResultCode() {
	}

	public ResultCode(int code) {
		this.code = code;
		if (code == FAIL) {
			this.message = "操作失败";
		}
	}
}
