package org.durcframework.common;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.durcframework.util.ResultUtil;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 统一的错误处理
 * @author hc.tang
 * 2014年6月6日
 *
 */
public class WebExceptionResolver implements HandlerExceptionResolver {

	private Logger logger = Logger.getLogger(this.getClass());
	
	@Override
	@JSONField(serialize = false)
	public ModelAndView resolveException(HttpServletRequest req,
			HttpServletResponse resp, Object obj, Exception e) {
		
		logger.error(e.getMessage() + "\r\nat method:"+obj.toString() , e);
		
		return ResultUtil.error(e.getMessage());
	}

}