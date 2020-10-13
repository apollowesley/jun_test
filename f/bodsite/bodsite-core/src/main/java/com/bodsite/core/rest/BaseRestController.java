package com.bodsite.core.rest;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.bodsite.common.exception.BaseException;
import com.bodsite.common.logger.Logger;
import com.bodsite.common.logger.LoggerFactory;
import com.bodsite.core.rest.http.ResponseUtil;

/**
 * 
* @Description: controller 基类
* @author bod
* @date 2016年12月20日 下午1:34:20 
*
 */
public class BaseRestController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	/**
	* 在每个request请求前执行 - 必须继承该类
	* @param request 
	* @author bod     
	 */
	@ModelAttribute
	public void beforeMethod(HttpServletRequest request ){
		
	}
	
	/**
	 * 运行期间抛出异常统一处理 - 必须继承该类
	* @param exception 
	* @author bod     
	* @throws
	 */
	@ExceptionHandler(value=BaseException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)  
	@ResponseBody 
	public String runTimeExceptionHandler(BaseException exp){
		logger.error(exp.getCode() + ":" + exp.getMessage(), exp); 
        return ResponseUtil.buildJsonStr(exp.getCode(),exp.getMessage());
	}
	
	/**
	 * 
	* 参数异常 - 用于捕获 @Valid 注解标记类抛出异常
	* @param exception 
	* @author bod     
	* @throws
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)  
	@ResponseBody 
	public String argumentBindExceptionHandler(MethodArgumentNotValidException exception) {
		BindingResult bindResult = exception.getBindingResult();
		List<FieldError> fieldErrors = bindResult.getFieldErrors();
		String fieldError = fieldErrors.get( 0 ).getDefaultMessage()  ;
		logger.error(fieldError, exception);
		return ResponseUtil.buildErrorJsonStr(fieldError);
	}
	
}
