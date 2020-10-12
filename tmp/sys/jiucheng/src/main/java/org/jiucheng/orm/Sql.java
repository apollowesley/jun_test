package org.jiucheng.orm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.jiucheng.log.Logger;

public class Sql {
	
    protected final static Logger log = Logger.getLogger(Sql.class);
    
	private StringBuilder sql;
	private List<Object> values;
	
	public Sql() {
		sql = new StringBuilder();
		values = new ArrayList<Object>();
	}
	
	public Sql(String sql) {
	    this();
		this.sql.append(sql);
	}
	
	public Sql clearSql() {
	    sql = new StringBuilder();
	    return this;
	}
	
	public Sql clearValues() {
	    values = new ArrayList<Object>();
	    return this;
	}
	
	public Sql append(String sql) {
		this.sql.append(sql);
		return this;
	}
	
	public Sql insertValue(Object obj) {
		values.add(obj);
		return this;
	}
	
	public String getSql() {
	    String s = sql.toString().replaceAll("\\s+", " ");
        log.info(s + " " + Arrays.toString(values.toArray()));
		return s;
	}
	
	public String getCountSql() {
		String rs = "SELECT COUNT(1) num FROM (" + sql.toString() + ") _tab";
		rs.replaceAll("\\s+", " ");
        log.info(rs + " " + Arrays.toString(values.toArray()));
		return rs;
	}
	
	public void setValues(List<Object> values) {
		this.values = values;
	}
	
	public List<Object> getValues() {
		return values;
	}
}
