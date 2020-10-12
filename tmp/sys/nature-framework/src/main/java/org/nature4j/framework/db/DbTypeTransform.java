package org.nature4j.framework.db;

import java.util.HashMap;
import java.util.Map;

import org.nature4j.framework.enums.Types;
import org.nature4j.framework.util.CastUtil;


public class DbTypeTransform {
	private static Map<Types,String> mysqlTransformMap = new HashMap<Types,String>(); 
	private static Map<Types,String> sqlserverTransformMap = new HashMap<Types,String>();
	private static Map<Types,String> oracleTransformMap = new HashMap<Types,String>();
	static{
		mysqlTransformMap.put(Types.STRING, "varchar");
		mysqlTransformMap.put(Types.INT, "int");
		mysqlTransformMap.put(Types.DOUBLE, "double");
		mysqlTransformMap.put(Types.FLOAT, "float");
		mysqlTransformMap.put(Types.DATETIME, "datetime");
		mysqlTransformMap.put(Types.BIGDECIMAL, "decimal");
	}
	static{
		sqlserverTransformMap.put(Types.STRING, "varchar");
		sqlserverTransformMap.put(Types.INT, "int");
		sqlserverTransformMap.put(Types.DOUBLE, "numeric");
		sqlserverTransformMap.put(Types.FLOAT, "float");
		sqlserverTransformMap.put(Types.DATETIME, "datetime");
		sqlserverTransformMap.put(Types.BIGDECIMAL, "decimal");
	}
	static{
		oracleTransformMap.put(Types.STRING, "varchar2");
		oracleTransformMap.put(Types.INT, "int");
		oracleTransformMap.put(Types.DOUBLE, "double");
		oracleTransformMap.put(Types.FLOAT, "float");
		oracleTransformMap.put(Types.DATETIME, "datetime");
		oracleTransformMap.put(Types.BIGDECIMAL, "decimal");
	}
	
	public static String getDbType(Types type, String columnLength,String jdbcDriver){
		String javaType= null;
		if (DbIdentifyer.isMySql(jdbcDriver)) {
			javaType = mysqlTransformMap.get(type);
			if (type == Types.STRING) {
				if (CastUtil.castInt(columnLength)>20765) {
					javaType = "text";
				}else {
					javaType=javaType+"("+columnLength+")";
				}
			}
		}else if (DbIdentifyer.isMsSql(jdbcDriver)) {
			javaType = sqlserverTransformMap.get(type);
			if (type == Types.STRING) {
				if (CastUtil.castInt(columnLength)>8000) {
					javaType = "text";
				}else {
					javaType=javaType+"("+columnLength+")";
				}
			}
		}else {
			javaType = oracleTransformMap.get(type);
			if (type == Types.STRING) {
				if (CastUtil.castInt(columnLength)>4000) {
					javaType = "clob";
				}else {
					javaType=javaType+"("+columnLength+")";
				}
			}
		}
		return javaType;
	}
}
