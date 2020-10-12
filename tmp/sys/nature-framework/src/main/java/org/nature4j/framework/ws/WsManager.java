package org.nature4j.framework.ws;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.ws.Endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WsManager {
	private static Logger LOGGER = LoggerFactory.getLogger(WsManager.class);
	
	public static void startWs(){
		
		Map<Class<?>, WsBean> wsMap = WsHelper.getWsMap();
		Set<Entry<Class<?>, WsBean>> wsBeanSet = wsMap.entrySet();
		if (!wsBeanSet.isEmpty()) {
			for (Entry<Class<?>, WsBean> wsBeanEntry : wsBeanSet) {
				WsBean wsBean = wsBeanEntry.getValue();
				String address = wsBean.getAddress();
				String namespace = wsBean.getNamespace();
				Object wsObject = wsBean.getWsObject();
				LOGGER.debug(address+namespace);
				Endpoint.publish(address+namespace, wsObject);
			}
		}
	}
}
