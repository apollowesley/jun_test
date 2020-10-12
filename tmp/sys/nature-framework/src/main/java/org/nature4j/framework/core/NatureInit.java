package org.nature4j.framework.core;

import com.alibaba.druid.pool.DruidDataSource;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.nature4j.framework.cache.CacheManager;
import org.nature4j.framework.cache.NatureCacheManager;
import org.nature4j.framework.db.DataSourceFactory;
import org.nature4j.framework.db.DbSSH;
import org.nature4j.framework.helper.*;
import org.nature4j.framework.quartz.QuartzJobHelper;
import org.nature4j.framework.restful.RestfulTransfer;
import org.nature4j.framework.util.StringUtil;
import org.nature4j.framework.ws.WsHelper;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Set;


public class NatureInit {
    static String[] dbs = ConfigHelper.getDb();
    public static void  init(){
        I18NHelper.initI18nMap();

        if (dbs!=null&&dbs.length>0){
            for (String db:dbs){
                if (StringUtil.isNotBank(ConfigHelper.getDbSSHHost(db))) {
                    DbSSH.init();
                    break;
                }
            }

        }
        TableBeanHelper.initTableBeans();
        InterceptorHelper.initInterceptor();
        ValidatorHelper.initValidator();
        ServiceHelper.initServices();
        CtrlHelper.initCtrls();
        RestfulTransfer.initRestBeans();
        WsHelper.initWs();
        QuartzJobHelper.initQuartzJob();
        TableAutoCreateHelper.initDbTable();
    }

    public static void destroy(){
        QuartzJobHelper.clear();
        WsHelper.clear();
        CtrlHelper.clear();
        ServiceHelper.clear();
        ValidatorHelper.clear();
        InterceptorHelper.clear();
        TableBeanHelper.clear();
        I18NHelper.clear();
        RestfulTransfer.clear();
        if (dbs!=null&&dbs.length>0){
            for (String db:dbs){
                if (StringUtil.isNotBank(ConfigHelper.getDbSSHHost(db))) {
                    DbSSH.destroy();
                    break;
                }
            }

        }
        if (ConfigHelper.getDb().length>0&&StringUtil.isNotBank(ConfigHelper.getJdbcDriver(ConfigHelper.getDb()[0]))) {
            Map<String, DataSource> dataSourceMap = DataSourceFactory.getDataSourceMap();
            Set<Map.Entry<String, DataSource>> entrySet = dataSourceMap.entrySet();
            for (Map.Entry<String, DataSource> entry : entrySet) {
                DataSource value = entry.getValue();
                if(value instanceof DruidDataSource){
                    DruidDataSource dds = (DruidDataSource) value;
                    dds.unregisterMbean();
                    dds.close();
                }else if (value instanceof ComboPooledDataSource) {
                    ComboPooledDataSource cpds = (ComboPooledDataSource) value;
                    cpds.close();
                }
            }
        }
        if (StringUtil.isNotBank(ConfigHelper.getCacheManager())) {
            NatureCacheManager cacheManager = CacheManager.getCacheManager();
            if (cacheManager!=null) {
                cacheManager.destroy();
            }
        }
    }
}
