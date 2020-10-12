/** Copyright 2013-2023 步步高商城. */
package com.taobao.diamond.client;

import com.taobao.diamond.domain.ConfigInfoEx;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author <a href="mailto:takeseem@gmail.com">杨浩</a>
 * @since 0.1.0
 */
public class BatchHttpResult {
	private boolean success = true;
	private String statusCode;
	private List<ConfigInfoEx> result;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public List<ConfigInfoEx> getResult() {
		return result;
	}

	public void addResult(String dataId, String group, String content) {
		if(result == null){
			result = new ArrayList<ConfigInfoEx>();
		}
		result.add(new ConfigInfoEx(dataId, group, content));

	}
}
