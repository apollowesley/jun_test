package com.imooc.util;

import java.sql.ResultSet;

/**
 * <p>
 * 数据映射接口
 * </p>
 *
 * @author yangkai.shen
 * @date Created in 2020/1/6 15:39
 */
public interface RowMapper<T> {
    /**
     * 行
     *
     * @param rs     数据
     * @param rowNum 行号
     * @return 对象
     * @throws Exception 异常
     */
    T mapRow(ResultSet rs, int rowNum) throws Exception;
}