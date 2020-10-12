package com.turingoal.generator.constant;

import freemarker.template.Configuration;
import freemarker.template.Version;

/**
 * 系统常量
 */
public interface TgConstantSystemValues {
    String DEFAULT_ENCODING = "utf-8"; // 默认编码
    Version FREE_MARKER_VERSION = Configuration.VERSION_2_3_30; // freeMarker 模板版本
}
