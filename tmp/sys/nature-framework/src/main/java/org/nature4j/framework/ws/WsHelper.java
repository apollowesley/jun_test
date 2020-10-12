package org.nature4j.framework.ws;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.jws.WebService;

import org.nature4j.framework.annotation.EndpointAddress;
import org.nature4j.framework.helper.ConfigHelper;
import org.nature4j.framework.helper.IocHelper;
import org.nature4j.framework.util.ClassUtil;
import org.nature4j.framework.util.ReflectionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WsHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(WsHelper.class);
	private static Map<Class<?>, WsBean> wsMap = new HashMap<Class<?>, WsBean>();
	@SuppressWarnings("restriction")
	public static void initWs(){
		Set<Class<?>> classSet = ClassUtil.getClassSet(ConfigHelper.getAppBasePackage(), WebService.class);
		for (Class<?> wsClass : classSet) {
			Object wsObject = ReflectionUtil.newInstance(wsClass);
			IocHelper.injectService(wsObject, wsClass);
			EndpointAddress endpointAddress = wsClass.getAnnotation(EndpointAddress.class);
			String address = endpointAddress.address();
			String namespace = endpointAddress.namespace();
			if ("-".equals(address)) {
				address = ConfigHelper.getEndpointAddress();
			}
			if ("-".equals(namespace)) {
				namespace ="/"+wsClass.getSimpleName();
			}
			wsMap.put(wsClass,  new WsBean(wsObject,address,namespace));
		}
		
		WsManager.startWs();
	}
	
	public static Map<Class<?>, WsBean> getWsMap(){
		return wsMap;
	}
	
	public static WsBean getWs(Class<?> wsClass){
		return wsMap.get(wsClass);
	}

	public static void clear() {
		wsMap.clear();
	}
}
