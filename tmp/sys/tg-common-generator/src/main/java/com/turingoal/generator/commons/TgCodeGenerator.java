package com.turingoal.generator.commons;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.turingoal.generator.core.domain.TgSqlTable;
import com.turingoal.generator.core.service.TgSqlTableService;
import com.turingoal.generator.util.TgCodeGeneratorUtil;

/**
 * TgCodeGenerator
 */
@Component
public final class TgCodeGenerator {
    @Autowired
    private TgSpringPropertiesSystem tgSpringPropertiesSystem;
    @Autowired
    private TgSqlTableService tgSqlTableService;

    /**
     * 生成代码
     */
    public void generate() throws Exception {
        List<TgSqlTable> tables = tgSqlTableService.listTablesWithColumns(tgSpringPropertiesSystem.getSchema()); // 查询所有表
        TgCodeGeneratorUtil.generate(tgSpringPropertiesSystem, tables, tgSpringPropertiesSystem.getShowDetailLogs());
    }
}
