package org.nature.framework.core;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.nature.framework.cache.CacheManagerFactory;
import org.nature.framework.handler.Hanlder;
import org.nature.framework.helper.ConfigHelper;
import org.nature.framework.helper.CtrlHelper;
import org.nature.framework.helper.I18NHelper;
import org.nature.framework.helper.InterceptorHelper;
import org.nature.framework.helper.ServiceHelper;
import org.nature.framework.helper.TableAutoCreateHelper;
import org.nature.framework.helper.TableBeanHelper;
import org.nature.framework.quartz.QuartzJobHelper;
import org.nature.framework.util.FilterUtil;
import org.nature.framework.util.StringUtil;
import org.nature.framework.ws.WsHelper;


@WebFilter(urlPatterns="/*",dispatcherTypes={DispatcherType.REQUEST,DispatcherType.FORWARD,DispatcherType.ERROR})
public class NatureFilter implements Filter {
	public void destroy() {
		if (StringUtil.isNotBank(ConfigHelper.getCacheManager())) {
			 CacheManagerFactory.getCacheManager().destroy(); 
		}
	} 

	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req; 
		HttpServletResponse response = (HttpServletResponse) res; 
		request.setCharacterEncoding("UTF-8");
		String targetKey = request.getServletPath();
		
		if (targetKey.indexOf(".") != -1||FilterUtil.isExclusion(request)) {
			chain.doFilter(request, response);
			return;
		}  
		Hanlder.doHandler(targetKey, request, response); 
	}
 
	public void init(FilterConfig fConfig) throws ServletException {
		I18NHelper.initI18nMap();
		TableBeanHelper.initTableBeans();
		InterceptorHelper.initInterceptor();
		ServiceHelper.initServices(); 
		CtrlHelper.initCtrls();
		TableAutoCreateHelper.initDbTable();
		WsHelper.initWs();
		QuartzJobHelper.initQuartzJob();
	}

}
