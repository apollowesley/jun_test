package com.auto.dbTools;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.auto.bean.CodeBean;
import com.auto.codeUtil.DbType;
import com.auto.util.ParseSqlXmlSingle;

@SuppressWarnings("all")
public class DBQueryUtil extends DBQueryBase {
	/**
	 *  根据表名查询列
	 * @param tableName
	 * @param tableNameSql
	 * @param queryTableInfoSql
	 * @return
	 * @throws SQLException
	 */
	public List queryColsByTableName(String tableName, String tableNameSql,
			String queryTableInfoSql,CodeBean param) throws SQLException {
		if(param.getDbType().equals(DbType.oracle)){
			Map paramMap = new HashMap();
			paramMap.put("table_name", tableName);
			//查询表
			List list = this.query(ParseSqlXmlSingle.getInstance()
					.get(tableNameSql), paramMap);
			//查询表的列信息
			if (list != null && list.size() > 0) {
				return this.query(
						ParseSqlXmlSingle.getInstance().get(queryTableInfoSql),
						paramMap);
			}
		}else if(param.getDbType().equals(DbType.mysql)){
			
//			Map paramMap = new HashMap();
//			paramMap.put("table_name", tableName);
//			//查询表
//			List list = this.query(ParseSqlXmlSingle.getInstance()
//					.get(tableNameSql), paramMap);
//			//查询表的列信息
//			if (list != null && list.size() > 0) {
//				return this.query(
//						ParseSqlXmlSingle.getInstance().get(queryTableInfoSql),
//						paramMap);
//			}
//			
//			Map paramMap = new HashMap();
//			paramMap.put("table_name", tableName);
			String sql = ParseSqlXmlSingle.getInstance().get(tableNameSql);
			sql += tableName;
			//查询表
			return this.query(sql, null);
			//查询表的列信息
//			if (list != null && list.size() > 0) {
//				String sql2=ParseSqlXmlSingle.getInstance().get(queryTableInfoSql);
//				sql2 += tableName;
//				return this.query(
//						sql2,
//						null);
//			}
		}
		return null;
	}
}
