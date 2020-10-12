package com.turingoal.generator.util;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;

/**
 * 类型映射工具类TgTypeMappingUtil
 */
public final class TgTypeMappingUtil {
    private static Logger logger = LoggerFactory.getLogger(TgTypeMappingUtil.class);
    private Map<String, String> mappingsMap;

    /**
     * 实例化，单例模式
     */
    private interface Singleton {
        TgTypeMappingUtil INSTANCE = new TgTypeMappingUtil();
    }

    /**
     * 获取实例
     */
    public static TgTypeMappingUtil getTgTypeMappingUtil() {
        return Singleton.INSTANCE;
    }

    /**
     * 获取所有映射关系
     */
    public Map<String, String> getAllMappings(final String mappingsFileName) {
        if (mappingsMap == null) {
            mappingsMap = new HashMap<String, String>();
        }
        if (StrUtil.isNotBlank(mappingsFileName)) {
            File file = new File("config/" + mappingsFileName);
            if (FileUtil.exist(file)) {
                Document document = XmlUtil.readXML(file);
                Element rootElement = XmlUtil.getRootElement(document);
                if (rootElement != null) {
                    List<Element> mappingElements = XmlUtil.transElements(rootElement.getChildNodes());
                    if (mappingElements != null && mappingElements.size() > 0) {
                        for (Element mappingElement : mappingElements) {
                            String sqlType = mappingElement.getAttribute("sqlType");
                            String type = mappingElement.getTextContent();
                            mappingsMap.put(sqlType, type);
                        }
                    }
                }
            } else {
                logger.error("类型映射文件不存在！请检查：config/" + mappingsFileName + "是否存在");
                System.exit(0); // 系统退出
            }
        } else {
            logger.error("类型映射文件没有配置！请配置参数：mappingsFileName");
            System.exit(0); // 系统退出
        }
        return mappingsMap;
    }

    /**
     * 获取类型
     */
    public static String getType(final String sqlType, final String defaultType, final String mappingsFileName) {
        Map<String, String> map = getTgTypeMappingUtil().getAllMappings(mappingsFileName);
        String type = map.get(sqlType);
        // 不存在则返回默认
        if (type == null) {
            type = defaultType;
        }
        return type;
    }
}
