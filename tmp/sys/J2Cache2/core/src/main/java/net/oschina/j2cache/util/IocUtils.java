/**
 * 
 */
package net.oschina.j2cache.util;

import org.springframework.context.ApplicationContext;

/**
 * @description <br>
 * @author <a href="mailto:vakinge@gmail.com">vakin</a>
 * @date 2016年10月26日
 */
public class IocUtils {

	private static ApplicationContext applicationContext;

	public static void setContext(ApplicationContext context) {
		IocUtils.applicationContext = context;
	}
	
	public static boolean springRedisRegistered(){
		return applicationContext != null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T getInstance(Class<T> beanClass) {
		String[] beanNames = applicationContext.getBeanNamesForType(beanClass);
		if (beanNames.length == 0) {
			return null;
		}
		return (T) applicationContext.getBean(beanNames[0]);
	}

	public static <T> T getInstance(Class<T> beanClass, String beanName) {
		return (T) applicationContext.getBean(beanName, beanClass);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getByBeanName(String beanName) {
		return (T) applicationContext.getBean(beanName);
	}
	
}
