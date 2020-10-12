
package codeGenerate.mybatis.generater.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import codeGenerate.mybatis.generater.AbstractGenerater;
import codeGenerate.mybatis.mybaitsJdbcType.JdbcTypeHandle;
import codeGenerate.mybatis.mybaitsJdbcType.imp.BLOB;
import codeGenerate.mybatis.mybaitsJdbcType.imp.CHAR;
import codeGenerate.mybatis.mybaitsJdbcType.imp.CLOB;
import codeGenerate.mybatis.mybaitsJdbcType.imp.DATE;
import codeGenerate.mybatis.mybaitsJdbcType.imp.LONGVARCHAR;
import codeGenerate.mybatis.mybaitsJdbcType.imp.NCHAR;
import codeGenerate.mybatis.mybaitsJdbcType.imp.NCLOB;
import codeGenerate.mybatis.mybaitsJdbcType.imp.NUMERIC;
import codeGenerate.mybatis.mybaitsJdbcType.imp.TIMESTAMP;
import codeGenerate.mybatis.mybaitsJdbcType.imp.VARCHAR;

public class OracleGenerater extends AbstractGenerater {

	@Override
	public Map<String, String> getColumnCommont() {
		Map<String, String> columnCommontMap = new HashMap<String, String>();
		Statement st = null;
		ResultSet rs = null;
		try {
			st = getConnection().createStatement();
			rs = st.executeQuery("select   column_name,comments   from   user_col_comments where table_name='" + super.getConfigVo().getTblName().toUpperCase() + "'");
			while (rs.next()) {
				columnCommontMap.put(rs.getString("COLUMN_NAME"), rs.getString("COMMENTS"));
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
		map.put("CHAR", new CHAR());//已经测试
		map.put("NCHAR", new NCHAR());//已经测试
		//VARCHAR,VARCHAR2
		map.put("VARCHAR2", new VARCHAR());//已经测试
		map.put("NVARCHAR2", new VARCHAR());//已经测试
		//NUMBER(NUMERIC,DECIMAL,SMALLINT是子类型)，number可以存放1E-130~10E125数字，numeric最大精度为38位
		map.put("NUMBER", new NUMERIC());//已经测试
		//时间
		map.put("TIMESTAMP", new TIMESTAMP());//已经测试
		map.put("DATE", new DATE());//已经测试
		map.put("BINARY_FLOAT", new NUMERIC());
		//大文本
		map.put("BLOB", new BLOB());//已经测试
		map.put("CLOB", new CLOB());//已经测试
		map.put("NCLOB", new NCLOB());//已经测试
		map.put("LONG", new LONGVARCHAR());//已经测试

		return map;
	}

}
