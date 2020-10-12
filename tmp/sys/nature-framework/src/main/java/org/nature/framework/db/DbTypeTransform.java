package org.nature.framework.db;

import java.util.HashMap;
import java.util.Map;

import org.nature.framework.enums.Types;


public class DbTypeTransform {
	private static Map<Types,String> mysqlTransformMap = new HashMap<Types,String>(); 
	private static Map<Types,String> sqlserverTransformMap = new HashMap<Types,String>();
	private static Map<Types,String> oracleTransformMap = new HashMap<Types,String>();
	static{
		mysqlTransformMap.put(Types.STRING, "varchar");
		mysqlTransformMap.put(Types.INT, "int");
		mysqlTransformMap.put(Types.DOUBLE, "double");
		mysqlTransformMap.put(Types.FLOAT, "float");
		mysqlTransformMap.put(Types.DATE, "datetime");
		mysqlTransformMap.put(Types.BIGDECIMAL, "decimal");
	}
	static{
		sqlserverTransformMap.put(Types.STRING, "varchar");
		sqlserverTransformMap.put(Types.INT, "int");
		sqlserverTransformMap.put(Types.DOUBLE, "numeric");
		sqlserverTransformMap.put(Types.FLOAT, "float");
		sqlserverTransformMap.put(Types.DATE, "datetime");
		sqlserverTransformMap.put(Types.BIGDECIMAL, "decimal");
	}
	static{
		oracleTransformMap.put(Types.STRING, "varchar");
		oracleTransformMap.put(Types.INT, "int");
		oracleTransformMap.put(Types.DOUBLE, "double");
		oracleTransformMap.put(Types.FLOAT, "float");
		oracleTransformMap.put(Types.DATE, "datetime");
		oracleTransformMap.put(Types.BIGDECIMAL, "decimal");
	}
	
	public static String getDbType(Types type){
		String javaType= null;
		if (DbIdentifyer.isMySql()) {
			javaType = mysqlTransformMap.get(type);
		}else if (DbIdentifyer.isMsSql()) {
			javaType = sqlserverTransformMap.get(type);
		}else {
			javaType = oracleTransformMap.get(type);
		}
		return javaType;
	}
}
