package cn.usbtg.session.impl;

import cn.usbtg.session.util.EmptyUtil;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.activerecord.DbKit;
import com.jfinal.plugin.activerecord.DbPro;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * db session dao 所需环境配置
 */
public class BTGDBSessionDaoConfig {

    private String dbConfigName; //jfinal配置数据源名称
    private String tableName = "_btgsession"; // 存放session的表名
    private String sessionIdColumnName = "si"; //存放sessionId的列名
    private String sessionObjColumnName = "so"; //存放sessionObj对象的列名
    private String sessionLastActiveTimeColumnName = "lat"; //存放最后使用时间列名

    /**
     * 获取jfinalDb操作类
     *
     * @return
     */
    public DbPro getDbPro() {
        if (EmptyUtil.isEmpty(this.dbConfigName)) {
            return DbPro.use();
        }
        return Db.use(this.dbConfigName);
    }

    public String getDbConfigName() {
        return dbConfigName;
    }

    public void setDbConfigName(String dbConfigName) {
        if (EmptyUtil.isNotEmpty(dbConfigName)) {
            this.dbConfigName = dbConfigName;
        }
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        if (EmptyUtil.isNotEmpty(tableName)) {
            this.tableName = tableName;
        }
    }

    public String getSessionIdColumnName() {
        return sessionIdColumnName;
    }

    public void setSessionIdColumnName(String sessionIdColumnName) {
        if (EmptyUtil.isNotEmpty(sessionIdColumnName)) {
            this.sessionIdColumnName = sessionIdColumnName;
        }
    }

    public String getSessionObjColumnName() {
        return sessionObjColumnName;
    }

    public void setSessionObjColumnName(String sessionObjColumnName) {
        if (EmptyUtil.isNotEmpty(sessionObjColumnName)) {
            this.sessionObjColumnName = sessionObjColumnName;
        }
    }

    public String getSessionLastActiveTimeColumnName() {
        return sessionLastActiveTimeColumnName;
    }

    public void setSessionLastActiveTimeColumnName(String sessionLastActiveTimeColumnName) {
        this.sessionLastActiveTimeColumnName = sessionLastActiveTimeColumnName;
    }
}