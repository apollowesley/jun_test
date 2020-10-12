package com.mini.jdbc.dialect;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.mini.jdbc.EntityInfo;
import com.mini.jdbc.Record;

/**
 * OracleDialect.
 */
public class OracleDialect extends Dialect {
	
	public String forTableBuilderDoBuild(String tableName) {
		return "select * from " + tableName + " where rownum < 1";
	}
	
	// insert into table (id,name) values(seq.nextval, ？)
	public void forModelSave(EntityInfo table, Map<String, Object> attrs, StringBuilder sql, List<Object> paras) {
		sql.append("insert into ").append(table.getName()).append("(");
		StringBuilder temp = new StringBuilder(") values(");
		String[] pKeys = table.getPrimaryKey();
		int count = 0;
		for (Entry<String, Object> e: attrs.entrySet()) {
			String colName = e.getKey();
			if (count++ > 0) {
				sql.append(", ");
				temp.append(", ");
			}
			sql.append(colName);
			Object value = e.getValue();
			if (value instanceof String && isPrimaryKey(colName, pKeys) && ((String)value).endsWith(".nextval")) {
			    temp.append(value);
			} else {
			    temp.append("?");
			    paras.add(value);
			}
		}
		sql.append(temp.toString()).append(")");
	}
	
	public String forModelDeleteById(EntityInfo table) {
		String[] pKeys = table.getPrimaryKey();
		StringBuilder sql = new StringBuilder(45);
		sql.append("delete from ");
		sql.append(table.getName());
		sql.append(" where ");
		for (int i=0; i<pKeys.length; i++) {
			if (i > 0)
				sql.append(" and ");
			sql.append(pKeys[i]).append(" = ?");
		}
		return sql.toString();
	}
	
	public void forModelUpdate(EntityInfo table, Map<String, Object> attrs, Set<String> modifyFlag, StringBuilder sql, List<Object> paras) {
		sql.append("update ").append(table.getName()).append(" set ");
		String[] pKeys = table.getPrimaryKey();
		for (Entry<String, Object> e : attrs.entrySet()) {
			String colName = e.getKey();
			if (modifyFlag.contains(colName)) {
				boolean isKey = false;
				for (String pKey : pKeys)	// skip primaryKeys
					if (pKey.equalsIgnoreCase(colName)) {
						isKey = true;
						break ;
					}
				
				if (isKey)
					continue ;
				
				if (paras.size() > 0)
					sql.append(", ");
				sql.append(colName).append(" = ? ");
				paras.add(e.getValue());
			}
		}
		sql.append(" where ");
		for (int i=0; i<pKeys.length; i++) {
			if (i > 0)
				sql.append(" and ");
			sql.append(pKeys[i]).append(" = ?");
			paras.add(attrs.get(pKeys[i]));
		}
	}
	
	public String forModelFindById(EntityInfo table, String columns) {
		StringBuilder sql = new StringBuilder("select ").append(columns).append(" from ");
		sql.append(table.getName());
		sql.append(" where ");
		String[] pKeys = table.getPrimaryKey();
		for (int i=0; i<pKeys.length; i++) {
			if (i > 0)
				sql.append(" and ");
			sql.append(pKeys[i]).append(" = ?");
		}
		return sql.toString();
	}
	
	public String forDbFindById(String tableName, String[] pKeys) {
		tableName = tableName.trim();
		trimPrimaryKeys(pKeys);
		
		StringBuilder sql = new StringBuilder("select * from ").append(tableName).append(" where ");
		for (int i=0; i<pKeys.length; i++) {
			if (i > 0)
				sql.append(" and ");
			sql.append(pKeys[i]).append(" = ?");
		}
		return sql.toString();
	}
	
	public String forDbDeleteById(String tableName, String[] pKeys) {
		tableName = tableName.trim();
		trimPrimaryKeys(pKeys);
		
		StringBuilder sql = new StringBuilder("delete from ").append(tableName).append(" where ");
		for (int i=0; i<pKeys.length; i++) {
			if (i > 0)
				sql.append(" and ");
			sql.append(pKeys[i]).append(" = ?");
		}
		return sql.toString();
	}
	
	public void forDbSave(StringBuilder sql, List<Object> paras, String tableName, String[] pKeys, Record record) {
		tableName = tableName.trim();
		trimPrimaryKeys(pKeys);
		
		sql.append("insert into ");
		sql.append(tableName).append("(");
		StringBuilder temp = new StringBuilder();
		temp.append(") values(");
		
		int count = 0;
		for (Entry<String, Object> e: record.entrySet()) {
			String colName = e.getKey();
			if (count++ > 0) {
				sql.append(", ");
				temp.append(", ");
			}
			sql.append(colName);
			
			Object value = e.getValue();
			if (value instanceof String && isPrimaryKey(colName, pKeys) && ((String)value).endsWith(".nextval")) {
			    temp.append(value);
			} else {
				temp.append("?");
				paras.add(value);
			}
		}
		sql.append(temp.toString()).append(")");
	}
	
	public void forDbUpdate(String tableName, String[] pKeys, Object[] ids, Record record, StringBuilder sql, List<Object> paras) {
		tableName = tableName.trim();
		trimPrimaryKeys(pKeys);
		
		sql.append("update ").append(tableName).append(" set ");
		for (Entry<String, Object> e: record.entrySet()) {
			String colName = e.getKey();
			if (!isPrimaryKey(colName, pKeys)) {
				if(record.getModifyFlag().contains(colName)){
					if (paras.size() > 0) {
						sql.append(", ");
					}
					sql.append(colName).append(" = ? ");
					paras.add(e.getValue());
				}
			}
		}
		sql.append(" where ");
		for (int i=0; i<pKeys.length; i++) {
			if (i > 0)
				sql.append(" and ");
			sql.append(pKeys[i]).append(" = ?");
			paras.add(ids[i]);
		}
	}
	
	public Object[] forPaginate(StringBuilder sql, int pageNumber, int pageSize, String select, Object[] paras) {
		int satrt = (pageNumber - 1) * pageSize + 1;
		int end = pageNumber * pageSize;
		int incre=2;
		Object[] _paras = new Object[paras.length+incre];
		System.arraycopy(paras, 0, _paras, 0, paras.length);
		_paras[_paras.length-2] = end;
		_paras[_paras.length-1] = satrt;
		sql.append("select * from ( select row_.*, rownum rownum_ from (  ");
		sql.append(select).append(" ");
		sql.append(" ) row_ where rownum <= ?").append(") table_alias");
		sql.append(" where table_alias.rownum_ >= ?");
		return _paras;
	}
	
	public boolean isOracle() {
		return true;
	}
	
	public void fillStatement(PreparedStatement pst, List<Object> paras) throws SQLException {
		for (int i=0, size=paras.size(); i<size; i++) {
			Object value = paras.get(i);
			if (value instanceof java.sql.Date)
				pst.setDate(i + 1, (java.sql.Date)value);
			else
				pst.setObject(i + 1, value);
		}
	}
	
	public void fillStatement(PreparedStatement pst, Object... paras) throws SQLException {
		for (int i=0; i<paras.length; i++) {
			Object value = paras[i];
			if (value instanceof java.sql.Date)
				pst.setDate(i + 1, (java.sql.Date)value);
			else if (value instanceof java.sql.Timestamp)
				pst.setTimestamp(i + 1, (java.sql.Timestamp)value);
			else
				pst.setObject(i + 1, value);
		}
	}
	
	public String getDefaultPrimaryKey() {
		return "ID";
	}
}
