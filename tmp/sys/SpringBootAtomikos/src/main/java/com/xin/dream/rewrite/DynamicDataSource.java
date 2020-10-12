package com.xin.dream.rewrite;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import com.xin.dream.rewrite.DataSourceContextHolder;

public class DynamicDataSource extends AbstractRoutingDataSource{
	/**
     * 取得当前使用那个数据源。
     */
    @Override
    protected Object determineCurrentLookupKey() {
        return DataSourceContextHolder.getDatasourceType();
    }
}
