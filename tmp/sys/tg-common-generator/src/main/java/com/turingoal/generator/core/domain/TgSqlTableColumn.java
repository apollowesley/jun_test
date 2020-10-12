package com.turingoal.generator.core.domain;

import java.io.Serializable;
import com.turingoal.generator.util.TgTypeMappingUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Data;

/**
 * [列]
 */
@Data
public class TgSqlTableColumn implements Serializable {
    private static final long serialVersionUID = 1L;
    private String columnName; // 列名
    private String remarks; // 列备注
    private Integer columnType; // 列类型
    private String columnTypeName; // 列类型名称
    private Integer sortOrder = 1; // 排序
    private String tableName; // 表名
    private Integer size; // 大小或数据长度
    private Boolean isNullable; // 是否为可空
    // 获取类型用
    private String mappingsFileName; // *必填* 类型映射文件名
    private String defultFieldType; // *必填* 默认字段类型，当找不到匹配类型的时候，使用默认类型

    public TgSqlTableColumn(final String mappingsFileNameParm, final String defultFieldTypeParm) {
        super();
        mappingsFileName = mappingsFileNameParm;
        defultFieldType = defultFieldTypeParm;
    }

    /**
     * 驼峰状命名
     */
    public String getCamelCaseName() {
        return StrUtil.toCamelCase(columnName);
    }

    /**
     * 帕斯卡命名
     */
    public String getPascalCaseName() {
        return StrUtil.upperFirst(StrUtil.toCamelCase(columnName));
    }

    /**
     * 下划线命名
     */
    public String getUnderlineCaseName() {
        return StrUtil.upperFirst(StrUtil.toUnderlineCase(columnName));
    }

    /**
     * 帕斯卡命名，为了兼容代码生成器
     */
    public String getFieldName() {
        return getPascalCaseName();
    }

    /**
     * 驼峰命名，为了兼容代码生成器
     */
    public String getFieldNameFirstLower() {
        return getCamelCaseName();
    }

    /**
     * 返回类型
     */
    public String getPossibleType() {
        return TgTypeMappingUtil.getType(columnTypeName, defultFieldType, mappingsFileName);
    }
}