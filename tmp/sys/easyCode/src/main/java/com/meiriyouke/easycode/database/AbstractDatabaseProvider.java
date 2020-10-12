package com.meiriyouke.easycode.database;

import java.sql.Connection;
import java.util.List;

import com.meiriyouke.easycode.config.Column;
import com.meiriyouke.easycode.utils.DBUtils;

/**
 * 数据库操作抽象类
 * 
 * User: liyd
 * Date: 13-12-6
 * Time: 上午11:13
 */
public abstract class AbstractDatabaseProvider implements DatabaseProvider {

    /**
     * 获取数据库表的信息
     *
     * @param tableName 表名
     * @return meta data
     */
    @Override
    public List<Column> getTableMetaData(String tableName) {

        Connection connection = DBUtils.getDefaultConnection();

        List<Column> list = getMetaData(tableName, connection);

        return list;
    }

    /**
     * 获取数据库表元信息
     *
     * @param tableName the table name
     * @param connection the connection
     * @return meta data
     */
    public abstract List<Column> getMetaData(String tableName, Connection connection);
}
