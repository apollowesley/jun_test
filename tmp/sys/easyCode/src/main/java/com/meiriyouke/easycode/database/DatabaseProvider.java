package com.meiriyouke.easycode.database;

import java.util.List;

import com.meiriyouke.easycode.config.Column;

/**
 * 数据库操作接口
 * 
 * User: liyd
 * Date: 13-12-6
 * Time: 上午10:30
 */
public interface DatabaseProvider {

    /**
     * 获取数据库表的信息
     *
     * @param tableName 表名
     * @return meta data
     */
    public List<Column> getTableMetaData(String tableName);
}
