package org.durcframework.util;

import java.util.List;

import org.durcframework.entity.ValidateHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.ModelAndView;

public class ResultUtil {

	private static final String JSON_NAME = "json";
	/**
	 * 返回json数据的jsp页面
	 */
	private static final String S_RESULT_JSP_NAME = "result";

	private static class Result {
		private boolean success;
		private String msg;
		private String errorMsg;
		private List<String> validateErrors;
		
		public static Result success(String msg){
			Result result = new Result();
			result.setSuccess(true);
			result.setMsg(msg);
			return result;
		}
		
		public static Result error(String errorMsg){
			Result result = new Result();
			result.setSuccess(false);
			result.setErrorMsg(errorMsg);
			return result;
		}
		
		public static Result error(String errorMsg,List<String> errors){
			Result result = error(errorMsg);
			result.setValidateErrors(errors);
			return result;
		}
		
		public boolean getSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public String getMsg() {
			return msg;
		}
		public void setMsg(String msg) {
			this.msg = msg;
		}
		public String getErrorMsg() {
			return errorMsg;
		}
		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}
		public List<String> getValidateErrors() {
			return validateErrors;
		}
		public void setValidateErrors(List<String> validateErrors) {
			this.validateErrors = validateErrors;
		}
		
	}

	/**
	 * 返回json格式字符串
	 * @param json json字符串
	 * @return
	 */
	public static ModelAndView buildModelAndView(String json) {
		if (StringUtils.hasText(json)) {
			return new ModelAndView(S_RESULT_JSP_NAME, JSON_NAME, json);
		}

		return success();
	}

	/**
	 * 返回出错信息
	 * @param errorMsg
	 * @return {"success":false,"errorMsg":"错误信息."}
	 */
	public static ModelAndView error(String errorMsg) {
		String json = JsonUtil.toJsonString(Result.error(errorMsg));
		return buildModelAndView(json);
	}

	/**
	 * 返回成功信息
	 * @return {"success":true}
	 */
	public static ModelAndView success() {
		return success(null);
	}
	
	/**
	 * 返回成功信息
	 * @return {"success":true}
	 */
	public static ModelAndView success(String msg) {
		String json = JsonUtil.toJsonString(Result.success(msg));
		return buildModelAndView(json);
	}
	
	/**
	 * 返回验证后的结果
	 * @param holder
	 * @return 如果验证正确,返回success()
	 */
	public static ModelAndView validateError(ValidateHolder holder){
		if(holder.isSuccess()){
			return success();
		}
		
		Result result = Result.error(null, holder.buildValidateErrors());
		
		String json = JsonUtil.toJsonString(result);
		
		return buildModelAndView(json);
	}
	
}
