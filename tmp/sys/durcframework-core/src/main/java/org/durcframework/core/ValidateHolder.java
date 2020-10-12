package org.durcframework.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * 验证结果
 * @author tanghc
 * 2014年6月19日
 * @param <T>
 *
 */
public class ValidateHolder {
	private boolean isSuccess;
	private Set<ConstraintViolation<Object>> constraintViolations;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Set<ConstraintViolation<Object>> buildConstraintViolations() {
		return constraintViolations;
	}

	public void setConstraintViolations(
			Set<ConstraintViolation<Object>> constraintViolations) {
		this.constraintViolations = constraintViolations;
	}
	
	// 返回格式类似于:["用户名错误","密码不正确"]
	public List<String> buildValidateErrors(){
		Set<ConstraintViolation<Object>> set = this.buildConstraintViolations();
		List<String> errors = new ArrayList<String>();
		
		for (ConstraintViolation<Object> c : set) {
			errors.add(c.getMessage());
		}
		
		return errors;
	}
	
	// hook for json
	public List<String> getErrors() {
		return this.buildValidateErrors();
	}
	
	/**
	 * 返回错误信息,多条错误使用splitChar隔开
	 * @param splitChar 如果为null,则使用","
	 * @return
	 */
	public String buildErrorMsg(String splitChar) {
		if(splitChar == null) {
			splitChar = ",";
		}
		List<String> errors = this.getErrors();
		if(errors.size() == 0) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		for (String error : errors) {
			sb.append(splitChar).append(error);
		}
		
		String errorMsg = sb.toString();
		
		return errorMsg.substring(1);
		
	}
	
	public String getErrorMsg() {
		return this.buildErrorMsg(null);
	}
	
}
