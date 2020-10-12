
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
import codeGenerate.mybatis.mybaitsJdbcType.imp.CLOB;
import codeGenerate.mybatis.mybaitsJdbcType.imp.DATE;
import codeGenerate.mybatis.mybaitsJdbcType.imp.FLOAT;
import codeGenerate.mybatis.mybaitsJdbcType.imp.INT;
import codeGenerate.mybatis.mybaitsJdbcType.imp.NCHAR;
import codeGenerate.mybatis.mybaitsJdbcType.imp.NUMERIC;
import codeGenerate.mybatis.mybaitsJdbcType.imp.NVARCHAR;
import codeGenerate.mybatis.mybaitsJdbcType.imp.SMALLINT;
import codeGenerate.mybatis.mybaitsJdbcType.imp.TIMESTAMP;
import codeGenerate.mybatis.mybaitsJdbcType.imp.TINYINT;
import codeGenerate.mybatis.mybaitsJdbcType.imp.VARCHAR;


public class SqlServer2005Generater extends AbstractGenerater {

	@Override
	public Map<String, String> getColumnCommont() {
		Map<String, String> columnCommontMap = new HashMap<String, String>();
		Statement st = null; 
		ResultSet rs = null;
		try {
			st = getConnection().createStatement();
			rs = st.executeQuery("SELECT  cast(B.name as varchar(500))AS COLUMN_NAME,cast(C.value as varchar(500))  AS COLUMN_COMMENT FROM sys.tables A INNER JOIN sys.columns B ON B.object_id = A.object_id LEFT JOIN sys.extended_properties C ON C.major_id = B.object_id AND C.minor_id = B.column_id WHERE A.name ='"+super.getConfigVo().getTblName().toUpperCase() +"'");
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
		//字符串
		map.put("CHAR", new CHAR());
		map.put("VARCHAR", new VARCHAR());
		map.put("TEXT", new CLOB());

		map.put("DATETIME", new TIMESTAMP());
		map.put("DATE", new DATE());
		map.put("INT", new INT());
		map.put("TINYINT", new TINYINT());
		map.put("SMALLINT", new SMALLINT());
		map.put("TIMESTAMP", new TIMESTAMP());
		map.put("DECIMAL", new NUMERIC());
		map.put("NUMERIC", new NUMERIC());
		map.put("BIGINT", new BIGINT());
		map.put("MONEY", new NUMERIC());
		map.put("SMALLMONEY", new NUMERIC());
		map.put("REAL", new NUMERIC());
		map.put("FLOAT", new FLOAT());
		//Unicode 字符串
		map.put("NCHAR", new NCHAR());
		map.put("NVARCHAR", new NVARCHAR());
		map.put("NTEXT", new CLOB());
		//二进制字符串
		map.put("BINARY", new BLOB());
		map.put("IMAGE", new BLOB());
		map.put("VARBINARY", new BLOB());
		map.put("SMALLDATETIME", new TIMESTAMP());
		
		return map;
	}
	
}
