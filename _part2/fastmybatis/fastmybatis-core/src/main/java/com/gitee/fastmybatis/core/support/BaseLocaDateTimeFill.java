package com.gitee.fastmybatis.core.support;

import com.gitee.fastmybatis.core.handler.BaseFill;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author tanghc
 */
public abstract class BaseLocaDateTimeFill extends BaseFill<LocalDateTime> {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");

    @Override
    protected LocalDateTime convertValue(Object columnValue) {
        if (columnValue == null) {
            return null;
        }
        if (columnValue instanceof Timestamp) {
            Timestamp timestamp = (Timestamp) columnValue;
            return timestamp.toLocalDateTime();
        } else {
            return LocalDateTime.parse(columnValue.toString(), DATE_TIME_FORMATTER);
        }
    }
}
