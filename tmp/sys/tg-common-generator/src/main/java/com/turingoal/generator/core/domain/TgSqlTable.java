package com.turingoal.generator.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * [表]
 */
@Data
public class TgSqlTable implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tableName; // 表名
    private String remarks; // 备注
    private List<TgSqlTableColumn> columns = new ArrayList<TgSqlTableColumn>(); // 列
    private List<String> prefixsNeedRemove;

    /**
     * 驼峰状命名
     */
    public String getCamelCaseName() {
        return StrUtil.toCamelCase(removePrefixs(tableName));
    }

    /**
     * 帕斯卡命名
     */
    public String getPascalCaseName() {
        return StrUtil.upperFirst(StrUtil.toCamelCase(removePrefixs(tableName)));
    }

    /**
     * 下划线命名
     */
    public String getUnderlineCaseName() {
        return StrUtil.upperFirst(StrUtil.toUnderlineCase(removePrefixs(tableName)));
    }

    /**
     * 类名，首字母小写。用的是驼峰命名，为了兼容代码生成器
     */
    public String getClassNameFirstLower() {
        return getCamelCaseName();
    }

    /**
     * 类名，用的是帕斯卡命名，为了兼容代码生成器
     */
    public String getClassName() {
        return getPascalCaseName();
    }

    /**
     * 移除前缀
     * 
     * @param str
     * @param prefixsNeedRemove
     */
    private String removePrefixs(final String str) {
        String result = str;
        if (prefixsNeedRemove != null && prefixsNeedRemove.size() > 0) {
            for (String prefixNeedRemove : prefixsNeedRemove) {
                String prefix = prefixNeedRemove.trim();
                if (result.startsWith(prefix)) {
                    result = StrUtil.removePrefix(result, prefix);
                    break;
                }
            }
        }
        return result;
    }
}