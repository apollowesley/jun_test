package org.simplestudio.restful.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import cucumber.runtime.io.ClasspathResourceIterable;
import cucumber.runtime.io.Resource;

public class PropertyUtil {

    //保存配置信息
    private static Map<String, String> PROPERTY_MAP = new HashMap<String, String>();

    //应用启动时静态块加载common.properties中的相关配置，然后根据module.name动态加载对应的模块
    static {
        ClasspathResourceIterable resourceIterable = new ClasspathResourceIterable(
                PropertyUtil.class.getClassLoader(), "", ".properties");
        loadResource(resourceIterable.iterator(), "common.properties");

        //加载模块自身的配置
        loadResource(resourceIterable.iterator(), PROPERTY_MAP.get("moduleName") + ".properties");
    }

    private static void loadResource(Iterator<Resource> it, String fileName) {
        Resource resource = null;
        while (it.hasNext()) {
            resource = it.next();
            if (resource.getPath().contains(fileName)) {
                InputStream is = null;

                try {
                    Properties prop = new Properties();
                    prop.load(resource.getInputStream());

                    Set<Object> keySet = prop.keySet();
                    Iterator<Object> keyIt = keySet.iterator();
                    while (keyIt.hasNext()) {
                        String key = (String) keyIt.next();
                        String value = prop.getProperty(key);

                        PROPERTY_MAP.put(key, value);
                    }
                } catch (Exception e) {
                    System.out.println("加载配置文件失败！");
                } finally {
                    if (is != null) {
                        try {
                            is.close();
                        } catch (IOException e) {//ignore

                        }
                    }
                }
                break;
            }
        }
    }

    public static String getProperty(String key) {
        return PROPERTY_MAP.get(key);
    }

}
