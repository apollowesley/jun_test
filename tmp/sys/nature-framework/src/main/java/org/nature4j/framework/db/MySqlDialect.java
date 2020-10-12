package org.nature4j.framework.db;

import org.nature4j.framework.bean.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MySqlDialect extends Dialect implements DialectIntferface {
	private static Logger LOGGER = LoggerFactory.getLogger(MySqlDialect.class);
	private MySqlDialect(){}
	private static MySqlDialect mySqlDialect= new MySqlDialect();
	public static MySqlDialect getInstance(){
		return mySqlDialect;
	}
	
	public String tranformPageSql(Page page, String sql) {
		int rowNum = page.getRowNum();
		StringBuffer newSql = new StringBuffer(sql);
		newSql.append(" LIMIT ").append(rowNum).append(",").append(page.getPageSize());
		return newSql.toString();
	}
	
}
