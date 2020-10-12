package org.nature.framework.ws;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.ws.Endpoint;

public class WsManager {
	
	public static void startWs(){
		Map<Class<?>, WsBean> wsMap = WsHelper.getWsMap();
		Set<Entry<Class<?>, WsBean>> wsBeanSet = wsMap.entrySet();
		if (!wsBeanSet.isEmpty()) {
			for (Entry<Class<?>, WsBean> wsBeanEntry : wsBeanSet) {
				WsBean wsBean = wsBeanEntry.getValue();
				String address = wsBean.getAddress();
				String namespace = wsBean.getNamespace();
				Object wsObject = wsBean.getWsObject();
				System.out.println(address+namespace);
				Endpoint.publish(address+namespace, wsObject);
			}
		}
	}
}
