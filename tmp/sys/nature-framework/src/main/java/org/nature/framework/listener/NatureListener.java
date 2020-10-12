package org.nature.framework.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.nature.framework.auth.AuthFilter;
import org.nature.framework.helper.ConfigHelper;
import org.nature.framework.util.FilterUtil;

@WebListener
public class NatureListener implements ServletContextListener {

    public void contextDestroyed(ServletContextEvent sce)  { 
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	String contextPath = sce.getServletContext().getContextPath();
    	sce.getServletContext().setAttribute("basePath", contextPath);
    	FilterUtil.initExcludes();
    	if (ConfigHelper.getAuth()) {
    		sce.getServletContext().addFilter("authFilter", AuthFilter.class).addMappingForUrlPatterns(null, false, "/*");
		}
    }
	
}
