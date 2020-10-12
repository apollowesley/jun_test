package com.kld.common.framework.result;

import java.util.List;

/**
 * 返回结果公共包装类
 * @author Administrator
 *
 * @param <T>
 */
public class CommonServiceResult<T> extends CommonResult <T>{
	
	private static final long serialVersionUID = 6373686873940240259L;
	private T data;
	private List<T> datas;
	

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public List<T> getDatas() {
		return datas;
	}

	public void setDatas(List<T> datas) {
		this.datas = datas;
	}

	public CommonServiceResult<T> data(T data) {
		setData(data);
		return this;
	}
	
	public CommonServiceResult<T> datas(List<T> datas) {
		setDatas(datas);
		return this;
	}
	
	public CommonServiceResult<T> total(int total) {
		setTotal(total);
		return this;
	}

	public CommonServiceResult<T> success(String msg) {
		if (null != msg) {
			setReturnMessage(msg);
		} else {
			setReturnMessage("操作成功!");
		}
		setReturnStatus(true);
		setReturnCode("50000");
		return this;
	}

	public CommonServiceResult<T> error(String errorCode,String msg) {
		if (null != msg) {
			setReturnMessage(msg);
		} else {
			setReturnMessage("操作失败!");
		}
		setReturnStatus(false);
		setReturnCode(errorCode);
		return this;
	}
}
