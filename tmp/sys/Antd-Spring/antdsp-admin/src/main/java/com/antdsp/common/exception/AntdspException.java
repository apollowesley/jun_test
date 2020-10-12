package com.antdsp.common.exception;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.antdsp.common.AntdspResponse;
import com.antdsp.common.ResponseCode;

/**
 * 
 * <p>title:AntdspException.java</p>
 * <p>Description: 自定义异常处理</p>  
 * <p>Copyright: Copyright (c) 2019</p> 
 * 
 * @author jt-lee
 * @date 2019年5月31日
 * @email a496401006@qq.com
 *
 */
@RestControllerAdvice
public class AntdspException extends RuntimeException{

	@ExceptionHandler(value= {UnauthorizedException.class})
	@ResponseBody
	public AntdspResponse unauthorizedException() {
		return new AntdspResponse( ResponseCode.FORBIDDEN , false , "权限不足，请联系管理员.");
	}
	
	@ExceptionHandler(value= {CaptchaException.class})
	@ResponseBody
	public AntdspResponse captchaException(CaptchaException e) {
		return new AntdspResponse( ResponseCode.ERROR , false , e.getMessage());
	}
	
	@ExceptionHandler(value= {AntdspErrorException.class})
	@ResponseBody
	public AntdspResponse errorException(AntdspErrorException e) {
		return new AntdspResponse( ResponseCode.ERROR , false , e.getMessage());
	}
}
