
package codeGenerate.mybatis.generater.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import codeGenerate.mybatis.generater.AbstractGenerater;
import codeGenerate.mybatis.mybaitsJdbcType.JdbcTypeHandle;
import codeGenerate.mybatis.mybaitsJdbcType.imp.BIGINT;
import codeGenerate.mybatis.mybaitsJdbcType.imp.BLOB;
import codeGenerate.mybatis.mybaitsJdbcType.imp.CHAR;
import codeGenerate.mybatis.mybaitsJdbcType.imp.DATE;
import codeGenerate.mybatis.mybaitsJdbcType.imp.DOUBLE;
import codeGenerate.mybatis.mybaitsJdbcType.imp.FLOAT;
import codeGenerate.mybatis.mybaitsJdbcType.imp.INT;
import codeGenerate.mybatis.mybaitsJdbcType.imp.NUMERIC;
import codeGenerate.mybatis.mybaitsJdbcType.imp.SMALLINT;
import codeGenerate.mybatis.mybaitsJdbcType.imp.TIME;
import codeGenerate.mybatis.mybaitsJdbcType.imp.TIMESTAMP;
import codeGenerate.mybatis.mybaitsJdbcType.imp.TINYINT;
import codeGenerate.mybatis.mybaitsJdbcType.imp.VARCHAR;
import codeGenerate.mybatis.utils.StringUtils;
import codeGenerate.mybatis.vo.ConfigVo;

public class MysqlGenerater extends AbstractGenerater {

	@Override
	public Map<String, String> getColumnCommont() {
		Map<String, String> columnCommontMap = new HashMap<String, String>();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = getConnection().createStatement();
		      ConfigVo configVo = super.getConfigVo();
		      String sql = "SELECT COLUMN_NAME,COLUMN_COMMENT from   INFORMATION_SCHEMA.COLUMNS where table_name='" + configVo.getTblName().toUpperCase() + "'";
		      if (StringUtils.isNotBlank(configVo.getDbName())) {
		        sql = sql + " and table_schema = '" + configVo.getDbName() + "'";
		      }			
			rs = st.executeQuery(sql);
			while (rs.next()) {
				columnCommontMap.put(rs.getString("COLUMN_NAME"), rs.getString("COLUMN_COMMENT"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return columnCommontMap;
	}

	@Override
	public Map<String, JdbcTypeHandle> registerTypeHandle() {
		Map<String, JdbcTypeHandle> map = new HashMap<>();

		map.put("CHAR", new CHAR());
		map.put("VARCHAR", new VARCHAR());
		map.put("TINYTEXT", new VARCHAR());
		map.put("TEXT", new VARCHAR());
		map.put("MEDIUMTEXT", new VARCHAR());
		map.put("LONGTEXT", new VARCHAR());

		//数字，mysql 无number
		map.put("TINYINT", new TINYINT());
		map.put("SMALLINT", new SMALLINT());
		map.put("MEDIUMINT", new INT());
		map.put("INT", new INT());
		map.put("BIGINT", new BIGINT());
		map.put("DOUBLE", new DOUBLE());
		map.put("FLOAT", new FLOAT());
		map.put("DECIMAL", new NUMERIC());
		map.put("NUMERIC", new NUMERIC());

		//二进制
		map.put("TINYBLOB", new BLOB());
		map.put("BLOB", new BLOB());
		map.put("LONGBLOB", new BLOB());
		map.put("MEDIUMBLOB", new BLOB());
		//时间
		map.put("DATETIME", new TIMESTAMP());
		map.put("DATE", new DATE());
		map.put("TIMESTAMP", new TIMESTAMP());
		map.put("YEAR", new INT());//问题
		map.put("TIME", new TIME());
		return map;
	}
}
