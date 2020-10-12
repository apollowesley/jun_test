package com.turingoal.generator.commons;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * 自定义配置 system
 */
@Data
@ConfigurationProperties(prefix = "tg.system")
public class TgSpringPropertiesSystem {
    private String appVerson; // 系统版本
    private String templateName; // *必填* 模板名称
    private String mappingsFileName; // *必填* 类型映射文件名
    private String defultFieldType = "String"; // *必填* 默认字段类型，当找不到匹配类型的时候，使用默认类型
    private Boolean showDetailLogs = false; // 是否显示详细日志
    private String projectName; // *必填* 项目名称
    private String basePackage; // *必填* 包名
    private String projectTitle; // 项目标题
    private String projectTitleShort; // 项目标题（短）
    private String projectDesc; // 项目描述
    private String schema; // 数据库schema
    private String prefixsNeedRemove; // 需要移除的前缀，英文逗号分隔
    private String ignoreTables; // 需要忽略的表，英文逗号分隔
    private String templateBaseDir; // 模板目录，下面可能有多套模板
    private String outputBaseDir;  // 输出目录
    private String datasourceDriver; // 驱动类型
    private String datasourceUrl; // *必填* 连接池url
    private String datasourceUsername; // *必填* 连接池用户名
    private String datasourcePassword;  // *必填* 连接池密码

    /**
     * 需要移除的前缀
     */
    public List<String> getPrefixsListNeedRemove() {
        List<String> result = null;
        if (StrUtil.isNotBlank(prefixsNeedRemove)) {
            result = StrUtil.split(prefixsNeedRemove, ',');
        }
        return result;
    }

    /**
     * 需要忽略的表
     */
    public List<String> getIgnoreTables() {
        List<String> result = null;
        if (StrUtil.isNotBlank(ignoreTables)) {
            result = StrUtil.split(ignoreTables, ',');
        }
        return result;
    }
}
