package com.jason.utils.email.gateway;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;
import com.jason.utils.email.Gateway;
import com.jason.utils.email.exception.GatewayInitializeException;

public class GatewayGroup {
	
	public final static List<Gateway> GATEWAYS=new ArrayList<Gateway>(0);

	static {
		Properties properties = new Properties();
		try {
			InputStream input=GatewayGroup.class.getResource("gateway_group.properties").openStream();
			properties.load(input);
			input.close();
		} catch (IOException e) {
			throw new GatewayInitializeException();
		}
		
		@SuppressWarnings("unchecked")
		Enumeration<String> propNames=(Enumeration<String>) properties.propertyNames();
		String propName;
		while(propNames.hasMoreElements()){
			propName=propNames.nextElement();
			try {
				Gateway gateway=(Gateway) Class.forName(properties.getProperty(propName)).newInstance();
				gateway.setText(propName);
				GATEWAYS.add(gateway);
			} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
				throw new GatewayInitializeException();
			}
		}
	}
	
	private GatewayGroup() {
	}
}