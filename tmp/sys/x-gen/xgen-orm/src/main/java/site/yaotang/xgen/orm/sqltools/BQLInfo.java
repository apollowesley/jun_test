package site.yaotang.xgen.orm.sqltools;

import java.util.List;

import lombok.Builder;
import lombok.Data;

/**
 * SQL语句包含信息
 * @author hyt
 * @date 2018年1月1日
 */
@Data
@Builder
public class BQLInfo {
	// 分析bql，帮助将bql转换为sql
	// form user where id = 1 ==> bql
	// select u.id,u.name from user as u where u.id = 1 ==> sql
	private String alias;
	private List<String> searchColumns;
	private String tableName;
	private List<String> conditions;
	private List<String> operations;
	private List<Object> values;
}
