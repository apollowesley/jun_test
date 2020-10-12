package org.nature4j.framework.plugin;

import javax.servlet.ServletContextEvent;

public interface NaturePlugin {
	
	public void start(ServletContextEvent sce);
	
	public void stop(ServletContextEvent sce);
	
}

