package cn.backflow.lib.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring上下文工具类
 *
 * @author backflow
 */
@SuppressWarnings("unchecked")
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context;

    public void setApplicationContext(ApplicationContext context) throws BeansException {
        SpringContextUtil.context = context;
    }

    public static <T> T getBean(String name, Class<T> requiredType) {
        return context.getBean(name, requiredType);
    }

    public static <T> T getBean(String name, Object... args) {
        return (T) context.getBean(name, args);
    }

    public static <T> T getBean(Class<T> requiredType) { return context.getBean(requiredType); }

    public static <T> T getBean(String name) {
        return (T) context.getBean(name);
    }

    public static boolean containsBean(String name) {
        return context.containsBean(name);
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }
}