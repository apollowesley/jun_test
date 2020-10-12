package org.nature4j.framework.plugin.hessian;

import java.net.MalformedURLException;

import com.caucho.hessian.client.HessianProxyFactory;

public class HessianClient {
	static HessianProxyFactory factory = new HessianProxyFactory();
	@SuppressWarnings("unchecked")
	public static <T> T create(String hessianUrl , Class<T> interfaceClass){
		try {
			return (T) factory.create(interfaceClass, hessianUrl);
		} catch (MalformedURLException e) {
			return null;
		}
	}
}
