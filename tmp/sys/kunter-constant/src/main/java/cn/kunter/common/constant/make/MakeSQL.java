/**
 * 
 */
package cn.kunter.common.constant.make;

import java.sql.Types;
import java.util.List;

import cn.kunter.common.constant.config.PropertyHolder;
import cn.kunter.common.constant.entity.Column;
import cn.kunter.common.constant.entity.Table;
import cn.kunter.common.constant.type.DBType;
import cn.kunter.common.constant.type.JdbcTypeNameTranslator;
import cn.kunter.common.constant.util.FileUtil;
import cn.kunter.common.constant.util.OutputUtilities;

/**
 * 根据表结构生成数据插入脚本
 * @author 阳自然
 * @version 1.0 2015年8月28日
 */
public class MakeSQL {

    // 数据库类型
    private final static String DB_TYPE = DBType.valueOf(PropertyHolder.getJDBCProperty("DB")).getValue();

    public static void main(String[] args) throws Exception {

        List<Table> tables = GetTableConfig.getTableConfig();
        MakeSQL.makerCreateTableSQL(tables);
    }

    /**
     * 数据脚本生成
     * @param table
     * @throws Exception
     * @author yangziran
     */
    public static void makerCreateTableSQL(List<Table> tables) throws Exception {

        StringBuilder builder = new StringBuilder();
        for (Table table : tables) {
            builder.append("-- ").append(table.getRemarks());
            OutputUtilities.newLine(builder);
            if (DB_TYPE.equals(DBType.ORACLE.getValue())) {
                // 判断表是否存在，存在执行删除
                builder.append("DROP TABLE \"").append(table.getTableName()).append("\" CASCADE CONSTRAINTS;");
                OutputUtilities.newLine(builder);
                // 建表
                builder.append("CREATE TABLE \"").append(table.getTableName()).append("\"(");
                OutputUtilities.newLine(builder);
                // 字段拼接
                for (int m = 0; m < table.getCols().size(); m++) {
                    Column column = table.getCols().get(m);
                    OutputUtilities.javaIndent(builder, 1);
                    builder.append("\"").append(column.getColumnName()).append("\" ");
                    builder.append(column.getSqlType());
                    if (JdbcTypeNameTranslator.getJdbcTypeName(Types.VARCHAR)
                            .equals(JdbcTypeNameTranslator.getJdbcType(column.getSqlType()))) {
                        builder.append("(").append(column.getLength()).append(")");
                    }
                    if (m < table.getCols().size() - 1) {
                        builder.append(",");
                    }
                    OutputUtilities.newLine(builder);
                }
                builder.append(");");
                OutputUtilities.newLine(builder);
                builder.append("COMMENT ON TABLE \"").append(table.getTableName()).append("\" IS '")
                        .append(table.getRemarks()).append("';");
                OutputUtilities.newLine(builder);
                for (Column column : table.getCols()) {
                    builder.append("COMMENT ON COLUMN \"").append(table.getTableName()).append("\".\"")
                            .append(column.getColumnName()).append("\" IS '").append(column.getRemarks()).append("';");
                    OutputUtilities.newLine(builder);
                }
                // 判断当前表有主键时
                if (table.getPrimaryKey() != null || table.getPrimaryKey().size() > 0) {
                    // 添加主键
                    builder.append("ALTER TABLE ").append(table.getTableName());
                    builder.append(" ADD PRIMARY KEY (");
                    for (int m = 0; m < table.getPrimaryKey().size(); m++) {
                        Column column = table.getPrimaryKey().get(m);
                        builder.append(column.getColumnName());
                        if (m < table.getPrimaryKey().size() - 1) {
                            builder.append(", ");
                        }
                    }
                    builder.append(");");
                    OutputUtilities.newLine(builder);
                    // 添加索引
                    builder.append("CREATE INDEX idx_");
                    builder.append(table.getTableName()).append(" using btree ON ").append(table.getTableName())
                            .append("(");
                    for (int m = 0; m < table.getPrimaryKey().size(); m++) {
                        Column column = table.getPrimaryKey().get(m);
                        builder.append(column.getColumnName());
                        if (m < table.getPrimaryKey().size() - 1) {
                            builder.append(", ");
                        }
                    }
                    builder.append(");");
                    OutputUtilities.newLine(builder);
                }
                OutputUtilities.newLine(builder);
            }
            else if (DB_TYPE.equals(DBType.MYSQL.getValue())) {
                // 判断表是否存在，存在执行删除
                builder.append("DROP TABLE IF EXISTS ").append(table.getTableName()).append(";");
                OutputUtilities.newLine(builder);
                // 建表
                builder.append("CREATE TABLE ").append(table.getTableName()).append("(");
                OutputUtilities.newLine(builder);
                // 字段拼接
                for (int m = 0; m < table.getCols().size(); m++) {
                    Column column = table.getCols().get(m);
                    OutputUtilities.javaIndent(builder, 1);
                    builder.append(column.getColumnName()).append(" ");
                    builder.append(column.getSqlType());
                    if (JdbcTypeNameTranslator.getJdbcTypeName(Types.VARCHAR)
                            .equals(JdbcTypeNameTranslator.getJdbcType(column.getSqlType()))) {
                        builder.append("(").append(column.getLength()).append(")");
                    }
                    builder.append(" COMMENT '").append(column.getRemarks()).append("'");
                    if (m < table.getCols().size() - 1) {
                        builder.append(",");
                    }
                    OutputUtilities.newLine(builder);
                }
                builder.append(")engine=innodb DEFAULT CHARSET=utf8;");
                OutputUtilities.newLine(builder);
                builder.append("ALTER TABLE ").append(table.getTableName()).append(" COMMENT '")
                        .append(table.getRemarks()).append("';");
                OutputUtilities.newLine(builder);
                // 判断当前表有主键时
                if (table.getPrimaryKey() != null || table.getPrimaryKey().size() > 0) {
                    // 添加主键
                    builder.append("ALTER TABLE ").append(table.getTableName());
                    builder.append(" ADD PRIMARY KEY (");
                    for (int m = 0; m < table.getPrimaryKey().size(); m++) {
                        Column column = table.getPrimaryKey().get(m);
                        builder.append(column.getColumnName());
                        if (m < table.getPrimaryKey().size() - 1) {
                            builder.append(", ");
                        }
                    }
                    builder.append(");");
                    OutputUtilities.newLine(builder);
                    // 添加索引
                    builder.append("CREATE INDEX idx_");
                    builder.append(table.getTableName()).append(" using btree ON ").append(table.getTableName())
                            .append("(");
                    for (int m = 0; m < table.getPrimaryKey().size(); m++) {
                        Column column = table.getPrimaryKey().get(m);
                        builder.append(column.getColumnName());
                        if (m < table.getPrimaryKey().size() - 1) {
                            builder.append(", ");
                        }
                    }
                    builder.append(");");
                    OutputUtilities.newLine(builder);
                }
                OutputUtilities.newLine(builder);
            }
            else if (DB_TYPE.equals(DBType.POSTGRESQL.getValue())) {
                // 判断表是否存在，存在执行删除
                builder.append("DROP TABLE IF EXISTS ").append(table.getTableName()).append(";");
                OutputUtilities.newLine(builder);
                // 建表
                builder.append("CREATE TABLE ").append(table.getTableName()).append("(");
                OutputUtilities.newLine(builder);
                // 字段拼接
                for (int m = 0; m < table.getCols().size(); m++) {
                    Column column = table.getCols().get(m);
                    OutputUtilities.javaIndent(builder, 1);
                    builder.append(column.getColumnName()).append(" ");
                    builder.append(column.getSqlType());
                    if (JdbcTypeNameTranslator.getJdbcTypeName(Types.VARCHAR)
                            .equals(JdbcTypeNameTranslator.getJdbcType(column.getSqlType()))) {
                        builder.append("(").append(column.getLength()).append(")");
                    }
                    if (m < table.getCols().size() - 1) {
                        builder.append(",");
                    }
                    OutputUtilities.newLine(builder);
                }
                builder.append(");");
                OutputUtilities.newLine(builder);
                builder.append("COMMENT ON TABLE ").append(table.getTableName()).append(" IS '")
                        .append(table.getRemarks()).append("';");
                OutputUtilities.newLine(builder);
                for (Column column : table.getCols()) {
                    builder.append("COMMENT ON COLUMN ").append(table.getTableName()).append(".")
                            .append(column.getColumnName()).append(" IS '").append(column.getRemarks()).append("';");
                    OutputUtilities.newLine(builder);
                }
                // 判断当前表有主键时
                if (table.getPrimaryKey() != null || table.getPrimaryKey().size() > 0) {
                    // 添加主键
                    builder.append("ALTER TABLE ").append(table.getTableName());
                    builder.append(" ADD PRIMARY KEY (");
                    for (int m = 0; m < table.getPrimaryKey().size(); m++) {
                        Column column = table.getPrimaryKey().get(m);
                        builder.append(column.getColumnName());
                        if (m < table.getPrimaryKey().size() - 1) {
                            builder.append(", ");
                        }
                    }
                    builder.append(");");
                    OutputUtilities.newLine(builder);
                    // 添加索引
                    builder.append("CREATE INDEX idx_");
                    builder.append(table.getTableName()).append(" using btree ON ").append(table.getTableName())
                            .append("(");
                    for (int m = 0; m < table.getPrimaryKey().size(); m++) {
                        Column column = table.getPrimaryKey().get(m);
                        builder.append(column.getColumnName());
                        if (m < table.getPrimaryKey().size() - 1) {
                            builder.append(", ");
                        }
                    }
                    builder.append(");");
                    OutputUtilities.newLine(builder);
                }
                OutputUtilities.newLine(builder);
            }
        }

        FileUtil.writeFile(PropertyHolder.getConfigProperty("target") + "CreateDataSQL.sql", builder.toString());
    }
}
