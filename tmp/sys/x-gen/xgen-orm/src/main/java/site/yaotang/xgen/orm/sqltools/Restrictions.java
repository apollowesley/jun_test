package site.yaotang.xgen.orm.sqltools;

import lombok.Builder;
import lombok.Data;

/**
 * （组合）条件
 * @author hyt
 * @date 2018年1月1日
 */
@Data
@Builder
public class Restrictions {

	private Restrictions r;
	private String condition;

	public static Restrictions eq(String column, Object value) {
		String condition = null;
		if (value instanceof String) {
			condition = column + "='" + value + "'";
		} else {
			condition = column + "=" + value;
		}
		return Restrictions.builder().condition(condition).build();
	}

	public static Restrictions ne(String column, Object value) {
		String condition = null;
		if (value instanceof String) {
			condition = column + "<>'" + value + "'";
		} else {
			condition = column + "<>" + value;
		}
		return Restrictions.builder().condition(condition).build();
	}

	public static Restrictions lt(String column, Object value) {
		String condition = null;
		if (value instanceof String) {
			condition = column + "<'" + value + "'";
		} else {
			condition = column + "<" + value;
		}
		return Restrictions.builder().condition(condition).build();
	}

	public static Restrictions le(String column, Object value) {
		String condition = null;
		if (value instanceof String) {
			condition = column + "<='" + value + "'";
		} else {
			condition = column + "<=" + value;
		}
		return Restrictions.builder().condition(condition).build();
	}

	public static Restrictions gt(String column, Object value) {
		String condition = null;
		if (value instanceof String) {
			condition = column + ">" + value + "'";
		} else {
			condition = column + ">" + value;
		}
		return Restrictions.builder().condition(condition).build();
	}

	public static Restrictions ge(String column, Object value) {
		String condition = null;
		if (value instanceof String) {
			condition = column + "=>'" + value + "'";
		} else {
			condition = column + "=>" + value;
		}
		return Restrictions.builder().condition(condition).build();
	}

	public static Restrictions in(String column, Object[] value) {
		String condition = column + " in (";
		for (int i = 0; i < value.length; i++) {
			Object v = value[i];
			if (v instanceof String) {
				condition += "'" + v + "'";
			} else {
				condition += v;
			}
			if (i < value.length - 1) {
				condition += ",";
			}
		}
		condition += ")";
		return Restrictions.builder().condition(condition).build();
	}

	public static Restrictions like(String column, Object value) {
		String condition = column + " like ";
		if (value instanceof String) {
			condition += "'%" + value + "%'";
		} else {
			condition += "%" + value + "%";
		}
		return Restrictions.builder().condition(condition).build();
	}

	public static Restrictions between(String column, Object start, Object end) {
		String condition = column + " between ";
		if (start instanceof String) {
			condition += "'" + start + "' and '" + end + "'";
		} else {
			condition += start + " and " + end;
		}
		return Restrictions.builder().condition(condition).build();
	}

	public static Restrictions and(Restrictions r1, Restrictions r2) {
		String condition = r1.getCondition() + " and " + r2.getCondition();
		return Restrictions.builder().condition(condition).build();
	}

	public static Restrictions or(Restrictions r1, Restrictions r2) {
		String condition = r1.getCondition() + " or " + r2.getCondition();
		return Restrictions.builder().condition(condition).build();
	}

	public static Restrictions not(Restrictions r) {
		String condition = " not (" + r.getCondition() + ")";
		return Restrictions.builder().condition(condition).build();
	}
}
