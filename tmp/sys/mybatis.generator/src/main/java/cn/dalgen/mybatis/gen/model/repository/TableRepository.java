package cn.dalgen.mybatis.gen.model.repository;

import cn.dalgen.mybatis.gen.model.config.CfTable;
import cn.dalgen.mybatis.gen.model.dbtable.Table;
import cn.dalgen.mybatis.gen.model.repository.db.MySQLTableRepository;
import cn.dalgen.mybatis.gen.model.repository.db.OBTableRepository;
import cn.dalgen.mybatis.gen.model.repository.db.OracleTableRepository;
import cn.dalgen.mybatis.gen.utils.CamelCaseUtils;
import cn.dalgen.mybatis.gen.utils.ConfigUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库字段信息解析
 * @author bangis.wangdf
 * @date 15/12/5
 */
public class TableRepository {
    /**
     * The constant .
     */
    private static final String[] TABLES_TYPE = {"TABLE"};

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

        if (cfTable == null || !StringUtils.equalsIgnoreCase(cfTable.getPhysicalName(), "dual")) {
            if (StringUtils.equalsIgnoreCase(ConfigUtil.getCurrentDb().getType(), "mysql")) {
                MySQLTableRepository tableRepository = new MySQLTableRepository();
                return tableRepository.gainTable(connection, tableName, cfTable);
            }else if (StringUtils.equalsIgnoreCase(ConfigUtil.getCurrentDb().getType(), "oracle")) {
                OracleTableRepository tableRepository = new OracleTableRepository();
                return tableRepository.gainTable(connection, tableName, cfTable);
            } else {
                MySQLTableRepository tableRepository = new MySQLTableRepository();
                return tableRepository.gainTable(connection, tableName, cfTable);
            }
        } else {
            Table table = new Table();
            String physicalName = cfTable == null ? tableName : cfTable.getPhysicalName();
            table.setSqlName(tableName);
            table.setPhysicalName(physicalName);
            table.setRemark(tableName);
            table.setJavaName(CamelCaseUtils.toCapitalizeCamelCase(tableName));
            return table;
        }

    }
}
