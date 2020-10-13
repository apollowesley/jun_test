package com.wangxin.common.framework.key.properties;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

/**
 * slave_key.properties工具类
 */
public class SlaveKeyProperties extends PropertyPlaceholderConfigurer {

    private static Map<String, String> propertiesMap;

    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props) throws BeansException {
        super.processProperties(beanFactoryToProcess, props);
        propertiesMap = new HashMap<String, String>();
        for (Object prop : props.keySet()) {
            String key = prop.toString();
            propertiesMap.put(key, props.getProperty(key));
        }
    }

    public static Map<String, String> getPropertiesMap() {
        return propertiesMap;
    }

    public static String getValue(String name) {
        Object value = propertiesMap.get(name);
        if (null != value)
            return value.toString();
        else
            return "";
    }

    public static String[] getValues(String name) {
        Object value = propertiesMap.get(name);
        if (null != value)
            return StringUtils.split(value.toString(), ",");
        else
            return null;
    }

}