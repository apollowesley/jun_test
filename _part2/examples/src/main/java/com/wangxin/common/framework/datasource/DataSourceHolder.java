package com.wangxin.common.framework.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DataSourceHolder {

    private static final Logger log = LoggerFactory.getLogger(DataSourceHolder.class);

    private static ThreadLocal<DataSourceEnum> currentDBName = new ThreadLocal<DataSourceEnum>();

    private static DataSourceEnum DEFAULT_HASH = DataSourceEnum.master;

    public static DataSourceEnum determineDefault() {
        return determineDS(DEFAULT_HASH);
    }

    public static DataSourceEnum determineDS(DataSourceEnum key) {
        currentDBName.set(key);
        log.debug("determineDS:" + key);
        return key;
    }

    public static DataSourceEnum getCurrentDBName() {
        return currentDBName.get();
    }
}
