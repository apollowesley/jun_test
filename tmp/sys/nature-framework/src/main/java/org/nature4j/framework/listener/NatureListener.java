package org.nature4j.framework.listener;

import org.nature4j.framework.auth.AuthFilter;
import org.nature4j.framework.helper.ConfigHelper;
import org.nature4j.framework.core.NatureInit;
import org.nature4j.framework.plugin.PluginHelper;
import org.nature4j.framework.util.FilterUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class NatureListener implements ServletContextListener {
	private static Logger LOGGER = LoggerFactory.getLogger(NatureListener.class);

	public void contextDestroyed(ServletContextEvent sce)  {
		NatureInit.destroy();
    	PluginHelper.stopPlugins(sce);  
    }

    public void contextInitialized(ServletContextEvent sce)  {
		NatureInit.init();
    	FilterUtil.initExcludes();
    	if (ConfigHelper.getAuth()) {
    		sce.getServletContext().addFilter("authFilter", AuthFilter.class).addMappingForUrlPatterns(null, false, "/*");
		}
    	PluginHelper.startPlugins(sce);
		LOGGER.debug("nature-framework init finished");
    }

}
