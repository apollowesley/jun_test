package com.turingoal.generator.core.service;

import java.util.List;
import com.turingoal.generator.core.domain.TgSqlTable;

/**
 * [表]Service
 */
public interface TgSqlTableService {
    /**
     * 查询所有[表],包括字段
     */
    List<TgSqlTable> listTablesWithColumns(final String schemaName);
}