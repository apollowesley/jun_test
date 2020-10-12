package cn.dalgen.mybatis.gen.model.repository.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import cn.dalgen.mybatis.gen.enums.TypeMapEnum;
import cn.dalgen.mybatis.gen.model.config.CfTable;
import cn.dalgen.mybatis.gen.model.dbtable.Table;
import cn.dalgen.mybatis.gen.model.config.CfColumn;
import cn.dalgen.mybatis.gen.model.dbtable.Column;
import cn.dalgen.mybatis.gen.model.dbtable.NormalIndex;
import cn.dalgen.mybatis.gen.model.dbtable.PrimaryKeys;
import cn.dalgen.mybatis.gen.model.dbtable.UniqueIndex;
import cn.dalgen.mybatis.gen.utils.CamelCaseUtils;
import cn.dalgen.mybatis.gen.utils.ConfigUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.type.JdbcType;

/**
 * Created by bangis.wangdf on 15/12/5. Desc
 */
public class MySQLTableRepository {

    private static final Map<String, String> tableRemarkMap = Maps.newConcurrentMap();

    /**
     * Gain table table.
     *
     * @param connection the connection
     * @param tableName  the table name
     * @param cfTable    the cf table
     * @return the table
     * @throws SQLException the sql exception
     */
    public Table gainTable(Connection connection, String tableName, CfTable cfTable)
        throws SQLException {
        String physicalName = cfTable == null ? tableName : cfTable.getPhysicalName();
        String logicName = tableName;
        for (String splitTableSuffix : ConfigUtil.getConfig().getSplitTableSuffixs()) {
            if (StringUtils.endsWithIgnoreCase(tableName, splitTableSuffix)) {
                logicName = StringUtils.replace(logicName, splitTableSuffix, "");
                break;
            }
        }

        List<CfColumn> cfColumns = cfTable == null ? null : cfTable.getColumns();
        DatabaseMetaData databaseMetaData = connection.getMetaData();

        //生成table
        Table table = new Table();
        table.setSqlName(logicName);

        String _pre = "";
        for (String pre : ConfigUtil.getCurrentDb().getTablePrefixs().keySet()) {
            //取最长的
            if (StringUtils.startsWith(logicName, StringUtils.upperCase(pre)) && pre.length() > _pre.length()) {
                _pre = pre;

            }
        }

        if (StringUtils.isNotBlank(_pre)) {
            //删除 or 替换
            String toTableName = ConfigUtil.getCurrentDb().getTablePrefixs().get(_pre)
                + StringUtils.substring(logicName, _pre.length());
            table.setJavaName(CamelCaseUtils.toCapitalizeCamelCase(toTableName));
        }

        if (StringUtils.isBlank(table.getJavaName())) {
            table.setJavaName(CamelCaseUtils.toCapitalizeCamelCase(logicName));
        }
        table.setPhysicalName(physicalName);

        String tableRemark = queryTableRemark(connection, tableName, logicName);

        table.setRemark(tableRemark);

        //填充字段
        fillColumns(physicalName, databaseMetaData, table, cfColumns,cfTable);

        //主键,唯一约束,索引
        fillPrimaryUniqueIndexKeys(connection, physicalName, databaseMetaData, table);

        //是否满足软删除
        if (ConfigUtil.getConfig().getDeleteColumn() != null) {
            for (Column column : table.getColumnList()) {
                if (StringUtils.equalsIgnoreCase(column.getSqlName(), ConfigUtil.getConfig()
                    .getDeleteColumn().getName())) {
                    table.setNeadSoftDelete(true);
                    break;
                }
            }
        }

        return table;
    }

    /**
     * 获取表中文注释
     *
     * @param connection
     * @param tableName
     * @param logicName
     * @return
     * @throws SQLException
     */
    private String queryTableRemark(Connection connection, String tableName, String logicName)
        throws SQLException {
        if (MapUtils.isEmpty(tableRemarkMap)) {
            final PreparedStatement pstmt = connection.prepareStatement("show table status");
            final ResultSet resultSet = pstmt.executeQuery();
            while (resultSet.next()) {
                tableRemarkMap.put(StringUtils.upperCase(resultSet.getString("NAME")),
                    resultSet.getString("COMMENT"));
            }
        }
        final String remark = tableRemarkMap.get(StringUtils.upperCase(tableName));
        return StringUtils.isBlank(remark) ? logicName : remark;
    }

    /**
     * 主键,唯一约束,索引. 支持联合主键,主键仅有一个字段是 主键名替换为字段名
     *
     * @param connection       the connection
     * @param tableName        the table name
     * @param databaseMetaData the database meta data
     * @param table            the table
     * @throws SQLException the sql exception
     */
    private void fillPrimaryUniqueIndexKeys(Connection connection, String tableName,
                                            DatabaseMetaData databaseMetaData, Table table)
        throws SQLException {

        //有哪些主键
        List<String> pkNameList = Lists.newArrayList();

        //有哪些唯一索引
        List<String> uniqueNameList = Lists.newArrayList();

        fillPrimaryKeys(connection, tableName, databaseMetaData, table, pkNameList);

        //唯一约束 用于生成 select update

        fillUniqueIndex(connection, tableName, databaseMetaData, table, pkNameList, uniqueNameList);

        //普通索引 用于生成 select
        fillNormalIndex(connection, tableName, databaseMetaData, table, pkNameList, uniqueNameList);
    }

    /**
     * 普通索引 用于生成 select
     *
     * @param connection
     * @param tableName
     * @param databaseMetaData
     * @param table
     * @param pkNameList
     * @param uniqueNameList
     * @throws SQLException
     */
    private void fillNormalIndex(Connection connection, String tableName,
                                 DatabaseMetaData databaseMetaData, Table table,
                                 List<String> pkNameList, List<String> uniqueNameList)
        throws SQLException {

        List<String> indexNameList = Lists.newArrayList();
        Map<String, NormalIndex> normalIndexMap = Maps.newHashMap();

        ResultSet indexResultSet = databaseMetaData.getIndexInfo(null, null, StringUtils.lowerCase(tableName), false,
            false);

        while (indexResultSet.next()) {
            String indexName = CamelCaseUtils.toCapitalizeCamelCase(ConfigUtil.getConfig().dealIndexName(getRsStr(indexResultSet,
                "INDEX_NAME")));
            if (pkNameList.contains(indexName) || uniqueNameList.contains(indexName)) {
                continue;
            }
            if (!indexNameList.contains(indexName)) {
                indexNameList.add(indexName);
            }
            //创建普通索引
            NormalIndex normalIndex = normalIndexMap.get(indexName);
            if (normalIndex == null) {
                normalIndex = new NormalIndex();
                normalIndexMap.put(indexName, normalIndex);
                table.addNormalIndex(normalIndex);
            }
            normalIndex.setIdxName(indexName);
            normalIndex.addColumn(table.getColumnByName(getRsStr(indexResultSet, "COLUMN_NAME")));
        }
    }

    /**
     * 唯一约束 用于生成 select update
     *
     * @param connection
     * @param tableName
     * @param databaseMetaData
     * @param table
     * @param pkNameList
     * @param uniqueNameList
     * @throws SQLException
     */
    private void fillUniqueIndex(Connection connection, String tableName,
                                 DatabaseMetaData databaseMetaData, Table table,
                                 List<String> pkNameList, List<String> uniqueNameList)
        throws SQLException {

        ResultSet uniqueResultSet = databaseMetaData.getIndexInfo(null, null, StringUtils.lowerCase(tableName), true,
            false);

        Map<String, UniqueIndex> uniqueIndexMap = Maps.newHashMap();

        while (uniqueResultSet.next()) {
            String uniqueName = CamelCaseUtils.toCapitalizeCamelCase(ConfigUtil.getConfig().dealIndexName(getRsStr(uniqueResultSet,
                "INDEX_NAME")));
            if (pkNameList.contains(uniqueName)) {
                continue;
            }
            if (!uniqueNameList.contains(uniqueName)) {
                uniqueNameList.add(uniqueName);
            }
            //创建唯一索引
            UniqueIndex uniqueIndex = uniqueIndexMap.get(uniqueName);
            if (uniqueIndex == null) {
                uniqueIndex = new UniqueIndex();
                uniqueIndexMap.put(uniqueName, uniqueIndex);
                table.addUniqueIndex(uniqueIndex);
            }
            uniqueIndex.setUkName(uniqueName);
            uniqueIndex.addColumn(table.getColumnByName(getRsStr(uniqueResultSet, "COLUMN_NAME")));
        }

    }

    /**
     * 填充主键
     *
     * @param connection
     * @param tableName
     * @param databaseMetaData
     * @param table
     * @param pkNameList
     * @throws SQLException
     */
    private void fillPrimaryKeys(Connection connection, String tableName,
                                 DatabaseMetaData databaseMetaData, Table table,
                                 List<String> pkNameList) throws SQLException {
        //支持联合主键
        Map<String, String> pkMaps = Maps.newHashMap();

        ResultSet resultSet = databaseMetaData.getPrimaryKeys(null, null, StringUtils.lowerCase(tableName));

        while (resultSet.next()) {
            String pkName = CamelCaseUtils.toCapitalizeCamelCase(getRsStr(resultSet, "PK_NAME"));
            pkMaps.put(getRsStr(resultSet, "COLUMN_NAME"), pkName);
            if (!pkNameList.contains(pkName)) {
                pkNameList.add(pkName);
            }

        }
        //主键只能有一个定义
        if (CollectionUtils.isNotEmpty(pkNameList)) {
            PrimaryKeys primaryKeys = new PrimaryKeys();
            String pkName = "";
            int pkCnt = pkMaps.keySet().size();
            for (String key : pkMaps.keySet()) {
                if (pkCnt == 1) {
                    pkName = CamelCaseUtils.toCapitalizeCamelCase(key);
                } else {
                    pkName = pkMaps.get(key);
                }
                primaryKeys.addColumn(table.getColumnByName(key));
            }
            primaryKeys.setPkName(pkName);
            table.setPrimaryKeys(primaryKeys);
        }
    }

    /**
     * Fill columns.
     *
     * @param tableName        the table name
     * @param databaseMetaData the database meta data
     * @param table            the table
     * @param cfColumns        the cf columns
     * @throws SQLException the sql exception
     */
    private void fillColumns(String tableName,
                             DatabaseMetaData databaseMetaData, Table table,
                             List<CfColumn> cfColumns,CfTable cfTable) throws SQLException {
        //指定表字段
        ResultSet resultSet = databaseMetaData.getColumns(null, null, StringUtils.lowerCase(tableName), null);
        //组装字段
        while (resultSet.next()) {

            Long ordinalPosition = resultSet.getLong("ORDINAL_POSITION");

            SimpleDateFormat formate = new SimpleDateFormat("yyyy-MM-dd");
            formate.format(new Date());
            if (cfTable != null && StringUtils.equals(formate.format(new Date()), cfTable.getOrdinalEffectiveDay())) {
               if( cfTable.getOrdinalMaxPosition() < ordinalPosition){
                   break;
               }
            }
            Column column = new Column();
            column.setSqlName(getRsStr(resultSet, "COLUMN_NAME"));
            column.setSqlType(JdbcType.forCode(resultSet.getInt("DATA_TYPE")).name());
            column.setDefaultValue(getRsStr(resultSet, "COLUMN_DEF"));
            column.setJavaName(CamelCaseUtils.toCamelCase(column.getSqlName()));
            column.setJavaType(getJavaType(column, cfColumns));
            column.setTestVal(getTestVal(column, cfColumns));
            column.setTypeHandler(getTypeHandler(column, cfColumns));
            column.setRemarks(getRsStr(resultSet, "REMARKS", column.getSqlName()));
            table.addColumn(column);
        }
    }

    private String getTypeHandler(Column column, List<CfColumn> cfColumns) {
        if (cfColumns != null && cfColumns.size() > 0) {
            for (CfColumn cfColumn : cfColumns) {
                if (StringUtils.equalsIgnoreCase(column.getSqlName(), cfColumn.getName())) {
                    return cfColumn.getTypeHandler();
                }
            }
        }
        return null;
    }

    private String getTestVal(Column column, List<CfColumn> cfColumns) {
        if (cfColumns != null && cfColumns.size() > 0) {
            for (CfColumn cfColumn : cfColumns) {
                if (StringUtils.equalsIgnoreCase(column.getSqlName(), cfColumn.getName())) {
                    return cfColumn.getTestVal();
                }
            }
        }
        return null;
    }

    /**
     * Gets java type.
     *
     * @param column    the column
     * @param cfColumns the cf columns
     * @return the java type
     */
    private String getJavaType(Column column, List<CfColumn> cfColumns) {
        if (cfColumns != null && cfColumns.size() > 0) {
            for (CfColumn cfColumn : cfColumns) {
                if (StringUtils.equalsIgnoreCase(column.getSqlName(), cfColumn.getName())) {
                    return cfColumn.getJavatype();
                }
            }
        }
        String javaType = TypeMapEnum.getByJdbcType(column.getSqlType()).getJavaType();
        String custJavaType = ConfigUtil.getConfig().getTypeMap().get(javaType);
        return StringUtils.isBlank(custJavaType) ? javaType : custJavaType;
    }

    /**
     * Str string.
     *
     * @param resultSet the result set
     * @param column    the column def
     * @return the string
     * @throws SQLException the sql exception
     */
    private String getRsStr(ResultSet resultSet, String column) throws SQLException {
        if (StringUtils.equals("REMARKS", column)) {
            return resultSet.getString(column);
        }
        return StringUtils.upperCase(resultSet.getString(column));
    }

    /**
     * Str string.
     *
     * @param resultSet  the result set
     * @param column     the column
     * @param defaultVal the default val
     * @return the string
     * @throws SQLException the sql exception
     */
    private String getRsStr(ResultSet resultSet, String column, String defaultVal)
        throws SQLException {
        String val = getRsStr(resultSet, column);
        return StringUtils.isBlank(val) ? defaultVal : val;
    }
}
