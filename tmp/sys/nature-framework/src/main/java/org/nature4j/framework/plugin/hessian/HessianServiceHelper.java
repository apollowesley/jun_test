package org.nature4j.framework.plugin.hessian;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import java.util.Set;

import org.nature4j.framework.helper.ConfigHelper;
import org.nature4j.framework.helper.ServiceHelper;
import org.nature4j.framework.util.ClassUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.caucho.hessian.server.HessianServlet;

public class HessianServiceHelper {

	
	private static Logger LOGGER = LoggerFactory.getLogger(HessianServiceHelper.class);
	private static Map<String, HessianServlet> hessianMap = new HashMap<String, HessianServlet>();

	public static void init(ServletConfig sc) {
		Set<Class<?>> classSet = ClassUtil.getClassSet(ConfigHelper.getAppBasePackage(), Hessian.class);
		for (Class<?> cls : classSet) {
			Hessian hessian = cls.getAnnotation(Hessian.class);
			String url = hessian.value();
			if (cls.isInterface()) {
				Map<Class<?>, Object> serviceMap = ServiceHelper.getServiceMap();
				if (!serviceMap.isEmpty()) {
					Set<Entry<Class<?>, Object>> entrySet = serviceMap.entrySet();
					for (Entry<Class<?>, Object> entry : entrySet) {
						if (cls.isAssignableFrom(entry.getKey())) {
							HessianServlet hessianServlet = new HessianServlet();
							hessianServlet.setHomeAPI(cls);
							hessianServlet.setHome(entry.getValue());
							try {
								hessianServlet.init(sc);
							} catch (ServletException e) {
								LOGGER.error("HessianServlet init ServletConfig error");
							}
							hessianMap.put(url,hessianServlet);
						}
					}
				}
			}else{
				LOGGER.error("@Hession annotated class is not a interface");
			}
			if(!hessianMap.isEmpty())
				LOGGER.debug("init hessians :"+hessianMap);
		}
	}
	
	public static HessianServlet getHessianMap(String url){
		return hessianMap.get(url);
	}
	
	public static void clear(){
		hessianMap.clear();
	}
}
