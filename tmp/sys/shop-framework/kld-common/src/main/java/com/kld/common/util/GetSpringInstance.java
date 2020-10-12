package com.kld.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;


/**
 * 功能描述：获得Spring实例.  <BR>
 * <B> 历史版本 :</B>
 * 开发者:   
 * 时间：11:42:13 AM  
 * 变更原因：    
 * 变化内容 ：
 * 首次开发时间：11:42:13 AM 描述：   版本：V1.0
 */
public class GetSpringInstance implements ApplicationContextAware {
	
	private final static Logger log = LoggerFactory.getLogger(GetSpringInstance.class);
	/** 变量描述：Spring的应用上下文. */
	private static ApplicationContext ctx;

	public static void setCtx(ApplicationContext ctx) {
		GetSpringInstance.ctx = ctx;
	}

	/**
	 * 实现说明：设置Spring的应用上下文. <BR>
	 * @param applicationContext：Spring的应用上下文
	 * @throws BeansException
	 * @see com.greatwe.jccp.util.common.GetSpringInstance
	 * @since 
	 */
	public void setApplicationContext(ApplicationContext applicationContext)throws BeansException {
		log.info("-------------");
		this.ctx = applicationContext;
	}

	/**
	 * 方法功能：获得Spring的应用上下文. <BR>
	 * 调用例子：ApplicationContext ac = GetSpringInstance.getCtx();
	 * @return Spring的上下文
	 * @see com.greatwe.jccp.util.common.GetSpringInstance
	 * @since 
	 */
	public static ApplicationContext getCtx() {
		log.info(" the spring resource factory ctx is :" +ctx.getId());
		return ctx;
	}

	/**
	 * 方法功能：获得spring工厂中的bean资源. <BR>
	 * 调用例子：GetSpringInstance.getBean(beanid);
	 * @param ctx：Spring的应用上下文
	 * @see com.greatwe.jccp.util.common.GetSpringInstance
	 * @since 
	 */
	public static Object getBean(String beanid) {
		log.info(" the spring resource factory ctx is :" +ctx.getId());
		return  ctx.getBean(beanid);
	}
	
	/**
	 * 方法功能：获得spring工厂中的bean资源. <BR>
	 * 调用例子：GetSpringInstance.getBean(beanid);
	 * @param ctx：Spring的应用上下文
	 * @see com.greatwe.jccp.util.common.GetSpringInstance
	 * @since 
	 */
	public static void refreshCtx(){
		if(ctx == null)((ConfigurableApplicationContext) ctx).refresh();
	}

}
