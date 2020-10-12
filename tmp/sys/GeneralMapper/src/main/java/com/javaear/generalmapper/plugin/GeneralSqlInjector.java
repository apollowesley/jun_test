package com.javaear.generalmapper.plugin;

import com.baomidou.mybatisplus.MybatisConfiguration;
import com.baomidou.mybatisplus.mapper.AutoSqlInjector;
import com.baomidou.mybatisplus.mapper.SqlMethod;
import com.baomidou.mybatisplus.toolkit.TableInfo;
import com.baomidou.mybatisplus.toolkit.TableInfoHelper;
import org.apache.ibatis.builder.MapperBuilderAssistant;
import org.apache.ibatis.session.Configuration;

/**
 * @author aooer
 */
public class GeneralSqlInjector extends AutoSqlInjector {

    /**
     * 自定义方法，注入点（子类需重写该方法）
     */
    @Override
    public void inject(Configuration configuration, MapperBuilderAssistant builderAssistant, Class<?> mapperClass,
                       Class<?> modelClass, TableInfo table) {
        this.configuration = configuration;
        this.builderAssistant = builderAssistant;
        this.languageDriver = configuration.getDefaultScriptingLanuageInstance();
        this.dbType = MybatisConfiguration.DB_TYPE;
        table = TableInfoHelper.initTableInfo(modelClass);
        /**
         * 没有指定主键，默认方法不能使用
         */
        if (table.getKeyProperty() != null) {
            /* 插入 */
            this.injectInsertOneSql(false, mapperClass, modelClass, table);
            this.injectInsertOneSql(true, mapperClass, modelClass, table);
            this.injectInsertBatchSql(mapperClass, modelClass, table);
            /* 删除 */
            this.injectDeleteSelectiveSql(mapperClass, modelClass, table);
            this.injectDeleteByMapSql(mapperClass, table);
            this.injectDeleteSql(false, mapperClass, modelClass, table);
            this.injectDeleteSql(true, mapperClass, modelClass, table);
            /* 修改 */
            this.injectUpdateByIdSql(false, mapperClass, modelClass, table);
            this.injectUpdateByIdSql(true, mapperClass, modelClass, table);
            this.injectUpdateSql(false, mapperClass, modelClass, table);
            this.injectUpdateSql(true, mapperClass, modelClass, table);
            this.injectUpdateBatchById(mapperClass, modelClass, table);

			/* 查询 */
            this.injectSelectSql(false, mapperClass, modelClass, table);
            this.injectSelectSql(true, mapperClass, modelClass, table);
            this.injectSelectByMapSql(mapperClass, modelClass, table);
            this.injectSelectOneSql(mapperClass, modelClass, table);
            this.injectSelectCountSql(mapperClass, modelClass, table);
            this.injectSelectListSql(SqlMethod.SELECT_LIST, mapperClass, modelClass, table);
            this.injectSelectListSql(SqlMethod.SELECT_PAGE, mapperClass, modelClass, table);
        } else {

            /**

             * 提示

             */

            System.err.println(String.format("%s ,The unknown primary key, cannot use the generic method", modelClass.toString()));
        }
    }
}