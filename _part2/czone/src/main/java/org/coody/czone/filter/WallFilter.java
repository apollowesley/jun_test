package org.coody.czone.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.coody.czone.common.constant.CacheFinal;
import org.coody.czone.task.WallQueue;
import org.coody.framework.cache.instance.LocalCache;
import org.coody.framework.core.container.BeanContainer;
import org.coody.framework.core.logger.BaseLogger;
import org.coody.framework.core.util.StringUtil;
import org.coody.framework.web.util.RequestUtil;


/**
 *
 * 拦截防止XSS跨站脚本攻击以及高频请求
 *
 * @author WebSOS
 * @time 2015-06-09
 */
public class WallFilter implements Filter {

	
	BaseLogger logger=BaseLogger.getLogger(WallFilter.class);
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	public void destroy() {
	}

	/**
	 * 過濾
	 */
	public void doFilter(ServletRequest req, ServletResponse resp,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		if(doWall(request)){
			//过滤Xss跨站脚本攻击
			chain.doFilter(req, resp);
		}
	}
	
	private static LocalCache localCache=null;
	//拦截CC攻击
	private boolean doWall(HttpServletRequest request){
		String ip=RequestUtil.getIpAddr(request);
		String userAgent=request.getHeader("User-Agent");
		logger.debug("{[收到请求]请求地址:"+request.getRequestURI()+",IP地址:"+ip+",User-Agent:"+userAgent+"}");
		String key=CacheFinal.WALL_IP_CACHE+ip.replace(".", "_");
		if(localCache==null){
			localCache=BeanContainer.getBean(LocalCache.class);
		}
		Integer num=(Integer) localCache.getCache(key);
		if(StringUtil.isNullOrEmpty(num)){
			num=0;
		}
		if(num>=300){
			localCache.setCache(key, 300,60*60*24);
			logger.info("IP:"+ip+"在系统黑名单，已禁止访问");
			//销毁session
			request.getSession().invalidate();
			WallQueue.writeWallIp(ip);
			return false;
		}
		localCache.setCache(key, num+1,30);
		return true;
	}
}