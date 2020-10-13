package tom.cocook.jdbc.simple;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;


import tom.cocook.jdbc.ProceRecord;
import tom.cocook.jdbc.ProcedureCallback;
import tom.kit.StringUtil;


/**
 * procedure工具
 *
 * @author dargoner
 */
public class ProceUtils {

    /**
     * ResultSet 转换List<Map>
     */
    public static List<Map<String, Object>> convertResultSetToMapList(ResultSet rs, Boolean toLowerCase) {
        List<Map<String, Object>> resultListMap = new ArrayList<Map<String, Object>>();

        ResultSetMetaData rsmd;

        if (rs == null)
            return resultListMap;
        try {
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                Map<String, Object> rowMap = new LinkedHashMap<String, Object>();
                for (int i = 1; i <= columnCount; i++) {
                    Object colValue = null;
                    String colName = rsmd.getColumnName(i);
                    if (toLowerCase) {
                        colName = colName.toLowerCase();
                    }
                    if (rsmd.getColumnType(i) == java.sql.Types.CLOB) {
                        colValue = rs.getClob(i);
                    } else if (rsmd.getColumnType(i) == java.sql.Types.BLOB) {
                        colValue = rs.getBlob(i);
                    } else {
                        colValue = rs.getObject(i);
                    }
                    rowMap.put(colName, colValue);
                }
                resultListMap.add(rowMap);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    rs = null;
                }
            }
        }
        return resultListMap;
    }

    /**
     * 存储过程输入参数绑定
     */
    public static void bindStatementInput(CallableStatement cs, int indexed, Object value) throws SQLException {
        if (value instanceof String) {
            cs.setString(indexed, (String) value);
        } else if (value instanceof Integer) {
            cs.setInt(indexed, ((Integer) value));
        } else if (value instanceof Double) {
            cs.setDouble(indexed, (Double) value);
        } else if (value instanceof Float) {
            cs.setFloat(indexed, ((Float) value));
        } else if (value instanceof Long) {
            cs.setLong(indexed, ((Long) value));
        } else if (value instanceof Boolean) {
            cs.setBoolean(indexed, ((Boolean) value));
        } else if(value instanceof java.sql.Timestamp){
        	cs.setTimestamp(indexed, new Timestamp(((java.util.Date) value).getTime()));
		}  else if (value instanceof java.util.Date) {
            cs.setDate(indexed, new java.sql.Date(((java.util.Date) value).getTime()));
        } else if (value instanceof BigDecimal) {
            cs.setBigDecimal(indexed, (BigDecimal) value);
        } else if (value instanceof Blob) {
            cs.setBlob(indexed, (Blob) value);
        } else if (value instanceof Clob) {
            cs.setClob(indexed, (Clob) value);
        } else { //可以直接一步
            cs.setObject(indexed, value);
        }
    }

    /**
     * 存储过程传出参数绑定
     */
    public static void bindStatementOutput(CallableStatement cs, int indexed, String dataType) throws SQLException {
        dataType = dataType.toLowerCase();

        if (StringUtil.equals(dataType, "string") || StringUtil.equals(dataType, "char")) {
            cs.registerOutParameter(indexed, Types.CHAR);
        } else if (StringUtil.equals(dataType, "int") || StringUtil.equals(dataType, "integer")) {
            cs.registerOutParameter(indexed, Types.INTEGER);
        } else if (StringUtil.equals(dataType, "date")) {
            cs.registerOutParameter(indexed, Types.DATE);
        } else if (StringUtil.equals(dataType, "timestamp")) {
            cs.registerOutParameter(indexed, Types.TIMESTAMP);
        } else if (StringUtil.equals(dataType, "decimal")) {
            cs.registerOutParameter(indexed, Types.DECIMAL);
        } else if (StringUtil.equals(dataType, "float")) {
            cs.registerOutParameter(indexed, Types.FLOAT);
        } else if (StringUtil.equals(dataType, "double")) {
            cs.registerOutParameter(indexed, Types.DOUBLE);
        } else if (StringUtil.equals(dataType, "cursor")) {// OracleTypes.CURSOR,Oralce游标作为out参数
            cs.registerOutParameter(indexed, -10);
        } else {
            cs.registerOutParameter(indexed, Types.CHAR);
        }
    }

    /**
     * 传出参数类型转换
     *
     * @throws SQLException
     */
    public static Object convertType(CallableStatement cs, int indexed, String dataType) throws SQLException {
        dataType = dataType.toLowerCase();

        Object dataValue = null;
        try {
            dataValue = cs.getObject(indexed);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        if (dataValue == null) {
            return null;
        }

        if (StringUtil.equals(dataType, "cursor")) {
            return ProceUtils.convertResultSetToMapList((ResultSet) dataValue, false); //oracle游标作为传出参数接收
        } else {
            return dataValue;
        }

    }
    
    
    
    /**
	 * ProcedureResult cr = executeProcedure("{call getSoft(?,name{string})}"
	 * ,"183" ); List<Map<String,Object>> list = cr.getDataSet(0);
	 * //返回结果集,返回参数都为大写,map.get("ID") System.out.println(cr.get("NAME")); //
	 * out参数获取,返回参数都为大写
	 * 
	 * @param conn
	 * @param sql
	 * @param paramArgs
	 * @return
	 * @throws SQLException
	 */
	public static ProceRecord executeProcedure(Connection conn, final String sql, Object... paramArgs) throws SQLException {

		return execProcedure(conn, sql, new ProcedureCallback() {

			/* 覆写paser(sql),解析出正确的sql */
			@Override
			public String parse(final String sql) {
				String callstr = sql.toLowerCase();

				String nameHead = StringUtil.substringBefore(callstr, "(");
				String paramterBody = StringUtil.substringBetween(callstr, "(", ")");

				StringBuffer callBuffer = new StringBuffer(50);
				if (!StringUtil.contains(sql, "call")) {
					callBuffer.append("{ call ");
				}
				callBuffer.append(nameHead);
				if (paramterBody != null) {
					callBuffer.append(" (");
					String[] paramterArray = paramterBody.split(",");
					for (int i = 0; i < paramterArray.length; i++) {
						String p = paramterArray[i].trim();
						if (!StringUtil.isEmpty(p)) {
							if (StringUtil.equals(p, "?")) { // 输入参数
								registerInParameter(); // 初始化输入参数
							} else { // 输出出参数
								String paramname = StringUtil.substringBefore(p, "{");
								String paramtype = StringUtil.substringBetween(p, "{", "}");

								paramname = StringUtil.defaultIfEmpty(paramname, "outvar");
								paramtype = StringUtil.defaultIfEmpty(paramtype, "string");

								registerOutParameter(paramtype, paramname); // 初始化输出参数

								paramterArray[i] = "?";
							}
						}
					}
					callBuffer.append(StringUtil.join(paramterArray, ","));
					callBuffer.append(")");
				}
				callBuffer.append("}");
				return callBuffer.toString();
			}
		}, paramArgs);
	}

	protected static ProceRecord execProcedure(Connection con, String sql, ProcedureCallback callback, Object... paramArgs) throws SQLException {
		ProceRecord result = null;
		if (callback == null) { // 复写parse(sql)可重写,sql表现形式可自己控制
			throw new RuntimeException("Callback object must not be null");
		}
		try {
			result = callback.doInCallback(con, sql, paramArgs);
		} catch (SQLException e) {
			throw e;
		} finally {
		}
		return result;
	}

}
