package com.javaear.json4bean.core;

import com.javaear.json4bean.util.*;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author aooer
 */
public class JsonRender {

    /*************** reander write bean ****************/

    /**
     * 渲染javaBean
     *
     * @param jsonStr   json字符串
     * @param className 类名
     */
    public void reander(String jsonStr, String className, String packageName, String destDir) throws IOException {
        String classBody = JsonSerializer.serialize(new JsonObject(jsonStr).getJsonObj(), className, StringUtils.PREFIX_SPACE);
        if (StringUtils.isCreateMultiBean)
            for (Map<String, String> multiBean : ArrayUtils.multiBeans)
                reanderBody(multiBean.get("classBody"), multiBean.get("className"), packageName, destDir);
        else
            reanderBody(classBody, className, packageName, destDir);
    }

    /**
     * 渲染javaBean
     *
     * @param classBody jsonObj
     * @param className 类名
     */
    void reanderBody(String classBody, String className, String packageName, String destDir) throws IOException {
        final String javaBeanContent = StringUtils.LINE + (StringUtils.isEmpty(MapUtils.CODE_TEMPLATE_MAP.get("class")) ? "" : StringUtils.LINE) + MapUtils.CODE_TEMPLATE_MAP.get("class") +
                classBody;
        IOUtils.write(MapUtils.CODE_TEMPLATE_MAP.get("copyright") + (StringUtils.isEmpty(MapUtils.CODE_TEMPLATE_MAP.get("copyright")) ? "" : StringUtils.LINE) +
                        (StringUtils.isEmpty(packageName) ? "" : "package " + packageName + ";" + StringUtils.LINE + StringUtils.LINE) +
                        (javaBeanContent.contains("private List<") ? "import java.util.List;" + javaBeanContent : javaBeanContent).trim(),
                destDir + File.separatorChar + className + ".java");
    }

    /*************** parse reverse ****************/

    /**
     * 解析bean为json字符串
     *
     * @param object bean
     * @return jsonString
     */
    public String toJsonString(Object object) {
        return JsonSerializer.serialize(BeanUtils.bean2Map(object));
    }

    /**
     * 解析json字符串为javaBean对象
     *
     * @param jsonStr     json字符串
     * @param targetClass 类名
     */
    public <T> T parseObject(String jsonStr, Class<T> targetClass) {
        return BeanUtils.map2Bean(new JsonObject(jsonStr).getJsonObj(), targetClass);
    }


    /***************
     * singleton
     ****************/

    /* 延迟加载内部类 */
    private static class JsonRenderHolder {
        private static JsonRender INSTANCE = new JsonRender();
    }

    /* 单例，实例获取方法 */
    public static JsonRender getInstance() {
        return JsonRenderHolder.INSTANCE;
    }

    private JsonRender() {
    }
}
