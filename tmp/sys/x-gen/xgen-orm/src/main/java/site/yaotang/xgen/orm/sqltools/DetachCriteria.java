package site.yaotang.xgen.orm.sqltools;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import site.yaotang.xgen.orm.mapping.Table;
import site.yaotang.xgen.orm.tools.Constant;

/**
 * SQL语句拼装
 * @author hyt
 * @date 2018年1月1日
 */
@Data
@Builder
public class DetachCriteria {

	private Class<?> clazz;
	private Table table;
	@Builder.Default
	private List<Restrictions> restrictionList = new ArrayList<>();

	/**
	 * 绑定实体
	 */
	public static DetachCriteria of(Class<?> clazz) {
		DetachCriteria criteria = DetachCriteria.builder().clazz(clazz).table(Constant.TABLEMAP.get(clazz.getName())).build();
		return criteria;
	}

	/**
	 * 添加条件
	 */
	public void addRestrictions(Restrictions r) {
		restrictionList.add(r);
	}

	/**
	 * 拼接SQL
	 */
	public String getSQLString() {
		StringBuilder sb = new StringBuilder();
		sb.append("select * from " + table.getTableName());
		if (restrictionList != null && restrictionList.size() > 0) {
			for (int i = 0; i < restrictionList.size(); i++) {
				if (i == 0) {
					sb.append(" where ");
				} else {
					sb.append(" and ");
				}
				sb.append(restrictionList.get(i).getCondition());
			}
		}
		return sb.toString();
	}
}
