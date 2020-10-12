package org.nature4j.framework.plugin;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.nature4j.framework.helper.ConfigHelper;
import org.nature4j.framework.util.ReflectionUtil;

public class PluginHelper {
	static Map<String, NaturePlugin> naturePluginMap = new HashMap<String, NaturePlugin>();
	public static void startPlugins(ServletContextEvent sce){
		String[] plugins = ConfigHelper.getPlugins();
		if (plugins!=null) {
			for (String pg : plugins) {
				NaturePlugin newInstance = (NaturePlugin) ReflectionUtil.newInstance(pg);
				naturePluginMap.put(pg, newInstance);
				newInstance.start(sce);
			}
		}
	}
	
	public static void stopPlugins(ServletContextEvent sce){
		Iterator<NaturePlugin> iterator = naturePluginMap.values().iterator();
		while (iterator.hasNext()) {
			NaturePlugin next = iterator.next();
			next.stop(sce);
		}
	} 
}
