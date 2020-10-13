package org.apache.center.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.baomidou.kisso.SSOHelper;
import com.baomidou.kisso.SSOToken;

 
/**
 * 
 * @author 20160405
 *
 */
public class DataLoadingInterceptor extends HandlerInterceptorAdapter {

	@Override
	public void postHandle( HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView ) throws Exception {
		/*
		 * 方法调用后调用该方法，返回数据给请求页
		 */
		if (isLegalView(modelAndView)) {
			SSOToken token = SSOHelper.attrToken(request);
			if (token != null) {
				String userName = token.getData();
				modelAndView.addObject("userName", userName);
				modelAndView.addObject("menuList", "dd");
			}
			
		}
	}

	
	/**
	 * 判断是否为合法的视图地址
	 * @param modelAndView
	 *            spring 视图对象
	 * @return boolean
	 */
	protected boolean isLegalView(ModelAndView modelAndView) {
		boolean legal = false;
		if (modelAndView != null) {
			String viewUrl = modelAndView.getViewName();
			if (viewUrl != null && viewUrl.contains("redirect:")) {
				legal = false;
			} else {
				legal = true;
			}
		}
		return legal;
	}
	
	
}