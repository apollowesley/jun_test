package com.site.util.properties;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	private Properties properties;
	public PropertiesUtil(String url) {
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(url));
			 properties = new Properties();
			properties.load(in);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Object getKey(String key) {
		return properties.get(key);
	}
}
