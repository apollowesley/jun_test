package com.gitee.fastmybatis.core.support;

import com.gitee.fastmybatis.core.handler.FillType;

import java.time.LocalDateTime;

/**
 * @author tanghc
 */
public class LocalDateTimeFillGmtModified extends BaseLocaDateTimeFill {

    private String columnName = "gmt_modified";

    public LocalDateTimeFillGmtModified() {
        super();
    }

    public LocalDateTimeFillGmtModified(String columnName) {
        super();
        this.columnName = columnName;
    }

    @Override
    public FillType getFillType() {
        return FillType.UPDATE;
    }

    @Override
    public LocalDateTime getFillValue(LocalDateTime defaultValue) {
        return LocalDateTime.now();
    }


    @Override
    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }


}
