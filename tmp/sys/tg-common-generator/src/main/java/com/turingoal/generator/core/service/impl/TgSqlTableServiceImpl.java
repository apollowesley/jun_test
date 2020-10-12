package com.turingoal.generator.core.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.turingoal.generator.commons.TgSpringPropertiesSystem;
import com.turingoal.generator.core.domain.TgSqlTable;
import com.turingoal.generator.core.domain.TgSqlTableColumn;
import com.turingoal.generator.core.service.TgSqlTableService;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import cn.hutool.db.meta.TableType;

/**
 * [表]Service
 */
@Service
public class TgSqlTableServiceImpl implements TgSqlTableService {
    private final Logger logger = LogManager.getLogger(TgSqlTableServiceImpl.class);
    @Autowired
    private DataSource dataSource;
    @Autowired
    private TgSpringPropertiesSystem tgSpringPropertiesSystem;

    /**
     * 查询所有[表],包括字段
     */
    @Override
    public List<TgSqlTable> listTablesWithColumns(final String schemaName) {
        List<TgSqlTable> tgSqlTables = new ArrayList<TgSqlTable>();
        List<String> tableNames = MetaUtil.getTables(dataSource, schemaName, TableType.TABLE);
        if (tableNames != null && tableNames.size() > 0) {
            for (String tableName : tableNames) {
                TgSqlTable tgSqlTable = new TgSqlTable();
                Table table = MetaUtil.getTableMeta(dataSource, tableName);
                tgSqlTable.setTableName(tableName);
                tgSqlTable.setRemarks(table.getComment());
                Collection<Column> columns = table.getColumns();
                if (columns != null && columns.size() > 0) {
                    int i = 0;
                    for (Column column : columns) {
                        i++;
                        TgSqlTableColumn tgSqlTableColumn = new TgSqlTableColumn(tgSpringPropertiesSystem.getMappingsFileName(), tgSpringPropertiesSystem.getDefultFieldType());
                        tgSqlTableColumn.setTableName(tableName);
                        tgSqlTableColumn.setColumnName(column.getName());
                        tgSqlTableColumn.setColumnType(column.getType());
                        tgSqlTableColumn.setColumnTypeName(column.getTypeName());
                        tgSqlTableColumn.setRemarks(column.getComment());
                        tgSqlTableColumn.setSize(column.getSize());
                        tgSqlTableColumn.setIsNullable(column.isNullable());
                        tgSqlTableColumn.setSortOrder(i);
                        if (tgSpringPropertiesSystem.getShowDetailLogs()) {
                            logger.info("字段信息  tableName:" + tableName + " --- columnName:" + column.getName() + " --- sqlType:" + column.getTypeName() + " --- possibleType:" + tgSqlTableColumn.getPossibleType());
                        }
                        tgSqlTable.getColumns().add(tgSqlTableColumn);
                    }
                }
                tgSqlTables.add(tgSqlTable);
            }
        }
        return tgSqlTables;
    }
}