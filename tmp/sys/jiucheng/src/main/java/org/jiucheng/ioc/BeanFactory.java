package org.jiucheng.ioc;

import org.jiucheng.exception.UncheckedException;
import org.jiucheng.log.Logger;

public class BeanFactory {
    
    private static final Logger log = Logger.getLogger(BeanFactory.class);
    
	static {
		IOC.init();
	}
	
	private BeanFactory() {}
	
	public static Object get(String beanName) {
		return IOC.get(beanName);
	}
	
	public static <T> T get(String beanName, Class<T> clazz) {
		Object rs = IOC.get(beanName);
		if(null != rs) {
			if(!clazz.isInstance(rs)) {
				throw new UncheckedException("Type mismatch");//类型不匹配
			}
		}
		return (T) rs;
	}
	
    public static <T> T getInstance(Class<T> clazz) {
	    String clazzName = clazz.getCanonicalName();
	    T rs = (T) IOC.get(clazzName);
	    if(null == rs) {
	        try {
	            rs = clazz.newInstance();
                IOC.put(clazzName, rs);
                IOC.injectFieldByBeanName(clazzName);
            } catch (InstantiationException e) {
                if(log.isErrorEnabled()) {
                    log.error("", e);
                }
            } catch (IllegalAccessException e) {
                if(log.isErrorEnabled()) {
                    log.error("", e);
                }
            }
	    }
        return rs;
	}
}
