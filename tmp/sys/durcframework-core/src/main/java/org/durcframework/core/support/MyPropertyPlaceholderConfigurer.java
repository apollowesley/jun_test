package org.durcframework.core.support;

import java.util.HashMap;
import java.util.Map;

/**
 * 扩展PropertyPlaceholderConfigurer,方便读取属性<br>
 * 使用方法:
 * <pre>
 * {@literal
<!-- spring的属性加载器，加载properties文件中的属性 -->
<bean id="propertyConfigurer"
	class="org.durcframework.core.support.MyPropertyPlaceholderConfigurer">
	<property name="location">
		<value>classpath:/config.properties</value>
	</property>
</bean>

然后调用MyPropertyPlaceholderConfigurer.getProperty(key);即可读取属性文件中的内容了

	}
 * </pre>
 * @author Administrator
 *
 */
public class MyPropertyPlaceholderConfigurer
		extends org.springframework.beans.factory.config.PropertyPlaceholderConfigurer {

	private static Map<String, String> propertiesData = new HashMap<String, String>();
	
	@Override
	protected String convertProperty(String propertyName, String propertyValue) {
		String value = super.convertProperty(propertyName, propertyValue);
		
		propertiesData.put(propertyName, value);
		
		return value;
	}
	
	/**
	 * 获取Properties文件中的值,如果不存在则返回defaultValue
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getProperty(String key,String defaultValue) {
		String value = propertiesData.get(key);
		return value == null ? defaultValue : value;
	}

	/**
	 * 获取Properties文件中的值,不存在返回null
	 * @param key
	 * @return
	 */
	public static String getProperty(String key) {
		return getProperty(key, null);
	}
	
}
