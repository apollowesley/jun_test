package com.foo.common.base.utils;

import java.util.List;

import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import net.sf.jsqlparser.statement.select.Select;
import net.sf.jsqlparser.util.TablesNamesFinder;

public class JSQLParserHelper {
	public static void main(String[] args) throws Exception {
		String sql = "select new Map(ect.id as id,ect.type as type,sd.dictName as typeTf,concat('http://"
				+ "localhost"
				+ "',img.url,img.imgName) as url) from EduOrgCourseType ect,SysImg img,SysDict sd where ect.id=img.fid and ect.type = sd.dictId and ect.orgId=? and img.type=? and ect.state=? and img.state=? ";
		Statement statement = CCJSqlParserUtil.parse(sql);
		Select selectStatement = (Select) statement;
		TablesNamesFinder tablesNamesFinder = new TablesNamesFinder();
		List<String> tableList = tablesNamesFinder
				.getTableList(selectStatement);
		FooUtils.printList(tableList);
	}
}
