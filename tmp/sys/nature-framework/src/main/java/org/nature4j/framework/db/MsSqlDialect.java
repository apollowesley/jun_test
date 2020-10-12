package org.nature4j.framework.db;

import org.nature4j.framework.bean.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MsSqlDialect extends Dialect implements DialectIntferface {
	private static Logger LOGGER = LoggerFactory.getLogger(MsSqlDialect.class);
	private MsSqlDialect(){}
	private static MsSqlDialect msSqlDialect= new MsSqlDialect();
	public static MsSqlDialect getInstance(){
		return msSqlDialect;
	}
	public String tranformPageSql(Page page, String sql) {
		int rowNum = page.getRowNum();
		String orderBySql="";
		try {
			orderBySql = sql.substring(sql.toLowerCase().lastIndexOf("order by"));
		} catch (Exception e) {
			LOGGER.error("page sql used in sqlserver must be with order by sql");
			throw new RuntimeException(e);
		}
		String noSelectSql =sql.substring(7, sql.length()).replace(orderBySql, "");
		StringBuffer pageSql = new StringBuffer();
		pageSql.append("SELECT * FROM  (SELECT TOP ").append(rowNum+page.getPageSize()).append(" ROW_NUMBER() OVER ( ");
		pageSql.append(orderBySql).append(" ) rownum, ").append(noSelectSql).append(" ) page WHERE page.rownum > ");
		pageSql.append(rowNum).append(" ORDER BY page.rownum");
		return pageSql.toString();
	}
	
}
