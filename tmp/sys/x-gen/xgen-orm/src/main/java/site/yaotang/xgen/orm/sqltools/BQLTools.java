package site.yaotang.xgen.orm.sqltools;

import java.util.ArrayList;
import java.util.List;

/**
 * SQL语句分析工具
 * @author hyt
 * @date 2018年1月1日
 */
public class BQLTools {
	// 分析bql，帮助将bql转换为sql
	// form user where id = 1 and name like '%z%' ==> bql
	// select u.id,u.name from user as u where u.id = 1 and u.name like '%z%' ==> sql

	// XXX 肯定有BUG，但是暂时先简单实现
	public static BQLInfo getBQLInfo(String bql) {
		String[] whereSplit = bql.split("where");
		String tableName = whereSplit[0].trim().replaceAll("from", "").replaceAll(" ", "");
		String alias = "_" + tableName;
		ArrayList<String> conditions = new ArrayList<>();
		ArrayList<String> operations = new ArrayList<>();
		ArrayList<Object> values = new ArrayList<>();
		List<String> keyWords = new ArrayList<String>();
		keyWords.add("=");
		keyWords.add("<>");
		keyWords.add(">");
		keyWords.add(">=");
		keyWords.add("<");
		keyWords.add("<=");
		keyWords.add("like");
		// keyWords.add("between");
		// keyWords.add("and");
		// keyWords.add("or");
		// keyWords.add("not");
		String[] whereRight = whereSplit[1].split(" ");
		for (int i = 0; i < whereRight.length; i++) {
			if (keyWords.contains(whereRight[i])) {
				conditions.add(whereRight[i - 1]);
				operations.add(whereRight[i]);
				values.add(whereRight[i + 1]);
			}
		}
		List<String> searchColumns = new ArrayList<>();
		searchColumns.add("*");
		return BQLInfo.builder().alias(alias).conditions(conditions).operations(operations).searchColumns(searchColumns).tableName(tableName).values(values).build();
	}

	public static String joinColumns(List<?> list) {
		StringBuffer sb = new StringBuffer("");
		for (Object object : list) {
			sb.append("," + String.valueOf(object));
		}
		return sb.substring(1);
	}

	public static String joinValues(List<?> list) {
		StringBuffer sb = new StringBuffer("");
		for (Object object : list) {
			if (object instanceof String) {
				sb.append(",'" + String.valueOf(object) + "'");
			} else {
				sb.append("," + String.valueOf(object));
			}
		}
		return sb.substring(1);
	}

	public static String joinColumnsValues(List<String> columns, List<Object> values) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < columns.size(); i++) {
			sb.append("," + columns.get(i) + "=");
			Object object = values.get(i);
			if (object instanceof String) {
				sb.append("'" + String.valueOf(object) + "'");
			} else {
				sb.append(String.valueOf(object));
			}
		}
		return sb.substring(1);
	}

}
