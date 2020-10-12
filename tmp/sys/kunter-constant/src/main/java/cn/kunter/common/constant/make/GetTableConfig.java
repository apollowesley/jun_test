/**
 * 
 */
package cn.kunter.common.constant.make;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import cn.kunter.common.constant.config.PropertyHolder;
import cn.kunter.common.constant.db.ConnectionFactory;
import cn.kunter.common.constant.entity.Column;
import cn.kunter.common.constant.entity.Table;
import cn.kunter.common.constant.type.DBType;
import cn.kunter.common.constant.type.JdbcTypeNameTranslator;
import cn.kunter.common.constant.type.SourceType;
import cn.kunter.common.constant.util.ExcelUtil;
import cn.kunter.common.constant.util.StringUtility;

/**
 * 获取数据库表结构
 * @author yangziran
 * @version 1.0 2014年11月16日
 */
public class GetTableConfig {

    // 数据源类型
    private final static String SOURCE_TYPE = SourceType.valueOf(PropertyHolder.getJDBCProperty("SourceType"))
            .getValue();
    // 数据库类型
    private final static String DB_TYPE = DBType.valueOf(PropertyHolder.getJDBCProperty("DB")).getValue();

    /**
     * 获取库表结构
     * @return
     * @throws SQLException
     * @author yangziran
     */
    public static List<Table> getTableConfig() throws Exception {

        if (SourceType.DB.getValue().equals(SOURCE_TYPE)) {
            return getDBTableConfig();
        }
        else {
            return getExcelTableConfig();
        }
    }

    /**
     * 获取数据源为数据库的库表表结构
     * @return
     * @throws SQLException
     * @author yangziran
     */
    public static List<Table> getDBTableConfig() throws SQLException {

        // 获取数据库连接
        Connection connection = ConnectionFactory.getConnection();

        String tableName = PropertyHolder.getConfigProperty("table");

        DatabaseMetaData metaData = connection.getMetaData();
        // 第一个参数：数据库名称，第二个参数：模式、登录名，第三个参数：表名称，第四个参数：类型(数组)
        ResultSet tables = metaData.getTables(
                connection.getCatalog(), metaData.getUserName(), DB_TYPE.equals(DBType.ORACLE.getValue())
                        ? tableName.toString().toUpperCase() : tableName.toString().toLowerCase(),
                new String[] { "TABLE" });

        List<Table> tableList = new ArrayList<Table>();
        while (tables.next()) {
            // 获取表信息
            Table table = new Table();
            String TABLE_NAME = tables.getString("TABLE_NAME");
            String REMARKS = tables.getString("REMARKS");
            table.setTableName(TABLE_NAME);
            table.setRemarks(REMARKS);

            // 获取到主键集合
            ResultSet key = metaData.getPrimaryKeys(connection.getCatalog(), metaData.getUserName(), TABLE_NAME);
            while (key.next()) {
                Column column = new Column();
                String COLUMN_NAME = key.getString("COLUMN_NAME");
                if (COLUMN_NAME.indexOf("‘") == 0 && COLUMN_NAME.lastIndexOf("’") > 1) {
                    COLUMN_NAME = COLUMN_NAME.substring(1, COLUMN_NAME.length() - 1);
                }
                column.setColumnName(COLUMN_NAME);
                table.addPrimaryKey(column);
            }
            key.close();

            // 获取到列集合
            ResultSet columns = metaData.getColumns(connection.getCatalog(), metaData.getUserName(), TABLE_NAME, null);
            while (columns.next()) {
                Column column = new Column();
                String COLUMN_NAME = columns.getString("COLUMN_NAME");
                if (COLUMN_NAME.indexOf("‘") == 0 && COLUMN_NAME.lastIndexOf("’") > 1) {
                    COLUMN_NAME = COLUMN_NAME.substring(1, COLUMN_NAME.length() - 1);
                }
                column.setSerial(String.valueOf(columns.getRow()));
                column.setColumnName(COLUMN_NAME);
                column.setSqlType(JdbcTypeNameTranslator.getJdbcTypeName(columns.getInt("DATA_TYPE")));
                column.setRemarks(columns.getString("REMARKS"));
                column.setLength(columns.getInt("COLUMN_SIZE"));
                table.addCols(column);
            }
            columns.close();

            tableList.add(table);
        }

        // 关闭连接
        tables.close();
        connection.close();

        for (Table table : tableList) {
            for (Column column1 : table.getPrimaryKey()) {
                for (Column column2 : table.getCols()) {
                    if (column1.getColumnName().equals(column2.getColumnName())) {
                        column1.setSqlType(column2.getSqlType());
                        column1.setRemarks(column2.getRemarks());
                    }
                }
            }
        }

        return tableList;
    }

    /**
     * 获取数据源为Excel的库表表结构
     * @return
     * @author yangziran
     * @throws Exception
     */
    public static List<Table> getExcelTableConfig() throws Exception {

        Workbook wb = ExcelUtil.getWorkbook();

        List<Table> tableList = new ArrayList<Table>();
        // 获取到总的Sheet循环
        for (int i = 2; i < wb.getNumberOfSheets(); i++) {
            // 获取到当前的Sheet对象
            Sheet sheet = wb.getSheetAt(i);
            // 获取到表名称
            String TABLE_NAME = sheet.getRow(1).getCell(7).getStringCellValue();
            // 获取到表物理名称
            String REMARKS = sheet.getRow(0).getCell(7).getStringCellValue();

            // 获取表信息
            Table table = new Table();
            table.setTableName(TABLE_NAME);
            table.setRemarks(REMARKS);

            // 获取到当前Sheet的最后一行下标加1为总行数 循环行
            for (int j = 5; j < sheet.getPhysicalNumberOfRows(); j++) {
                // 获取到当前行对象
                Row row = sheet.getRow(j);
                // 编号
                String serial = row.getCell(0).getStringCellValue();
                // 列名
                String columnName = row.getCell(8).getStringCellValue();
                // 物理名
                String physical = row.getCell(2).getStringCellValue();
                // 类型
                String type = row.getCell(13).getStringCellValue().toUpperCase();
                // 主键
                String primaryKey = row.getCell(23).getStringCellValue();
                // 长度
                Integer length = null;
                Cell cell = row.getCell(18);
                Double cellVal = cell.getNumericCellValue();
                if (null != cellVal) {
                    length = (int) cell.getNumericCellValue();
                }

                Column column = new Column();
                column.setSerial(serial);
                column.setColumnName(columnName);
                column.setSqlType(JdbcTypeNameTranslator.getJdbcTypeName(JdbcTypeNameTranslator.getJdbcType(type)));
                column.setRemarks(physical);
                column.setLength(length);

                // 主键列不为空
                if (StringUtility.isNotEmpty(primaryKey)) {
                    table.addPrimaryKey(column);
                }
                table.addCols(column);
            }

            tableList.add(table);
        }

        return tableList;
    }
}
