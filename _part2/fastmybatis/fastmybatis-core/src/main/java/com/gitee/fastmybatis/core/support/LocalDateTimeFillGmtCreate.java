package com.gitee.fastmybatis.core.support;

import com.gitee.fastmybatis.core.handler.FillType;

import java.time.LocalDateTime;

/**
 * @author tanghc
 */
public class LocalDateTimeFillGmtCreate extends BaseLocaDateTimeFill {


    private String columnName = "gmt_create";

    public LocalDateTimeFillGmtCreate() {
        super();
    }

    public LocalDateTimeFillGmtCreate(String columnName) {
        super();
        this.columnName = columnName;
    }

    @Override
    public FillType getFillType() {
        return FillType.INSERT;
    }

    @Override
    public LocalDateTime getFillValue(LocalDateTime defaultValue) {
        if (defaultValue == null) {
            defaultValue = LocalDateTime.now();
        }
        return defaultValue;
    }


    @Override
    public String getColumnName() {
        return columnName;
    }
}
