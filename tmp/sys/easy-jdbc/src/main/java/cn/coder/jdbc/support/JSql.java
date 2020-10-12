package cn.coder.jdbc.support;

import java.util.ArrayList;
import java.util.List;

/**
 * 复杂SQL构造类
 * 
 * @author YYDF 2019-07-29
 */
public final class JSql {

	private final List<StringBuilder> temps;
	private final List<Object> args;

	private JSql(String[] sqls, Object[] array) {
		args = new ArrayList<>();
		temps = new ArrayList<>(sqls.length);
		for (String sql : sqls) {
			temps.add(new StringBuilder(sql));
		}
		for (Object obj : array) {
			args.add(obj);
		}
	}

	public static JSql create(String fetchSql, Object... args) {
		if (notEmpty(fetchSql))
			return new JSql(new String[] { fetchSql }, args);
		throw new NullPointerException("Args 'fetchSql' can not be null");
	}

	public static JSql create(String fetchSql, String countSql, Object... args) {
		if (notEmpty(fetchSql) && notEmpty(countSql))
			return new JSql(new String[] { fetchSql, countSql }, args);
		throw new NullPointerException("Args 'fetchSql' and 'countSql' can not be null");
	}

	public JSql flike(Object arg, String field) {
		return like(arg, " AND " + field + " LIKE ?");
	}

	public JSql like(Object arg, String condition) {
		return like(arg, condition, 1);
	}

	public JSql like(Object arg, String condition, int num) {
		if (notEmpty(arg)) {
			arg = "%" + arg.toString() + "%";
			for (StringBuilder sql : temps) {
				sql.append(condition);
			}
			for (int i = 0; i < num; i++) {
				args.add(arg);
			}
		}
		return this;
	}

	public void eq(String condition) {
		for (StringBuilder sql : temps) {
			sql.append(condition);
		}
	}

	public JSql feq(Object arg, String filed) {
		return eq(arg, " AND " + filed + " = ?");
	}

	public JSql eq(Object arg, String condition) {
		return eq(arg, condition, 1);
	}

	public JSql eq(Object arg, String condition, int num) {
		if (notEmpty(arg)) {
			for (StringBuilder sql : temps) {
				sql.append(condition);
			}
			for (int i = 0; i < num; i++) {
				args.add(arg);
			}
		}
		return this;
	}

	public JSql fbetween(Object start, Object end, String field) {
		return fbetween(start, end, field, false);
	}

	public JSql fbetween(Object start, Object end, String field, boolean appendTime) {
		return between(start, end, " AND " + field + " BETWEEN ? AND ?", appendTime, 1);
	}

	public JSql between(Object start, Object end, String condition) {
		return between(start, end, condition, false, 1);
	}

	public JSql between(Object start, Object end, String condition, boolean appendTime) {
		return between(start, end, condition, appendTime, 1);
	}

	/**
	 * 增加 BETWEEN ? AND ? 条件
	 * @param start 开始
	 * @param end 结束
	 * @param condition 条件，例如(BETWEEN ? AND ?)
	 * @param appendTime 是否追加时间，只有start和end是日期字符串时可用
	 * @param num 参数循环几次
	 * @return 当前构建好的JSql对象
	 */
	public JSql between(Object start, Object end, String condition, boolean appendTime, int num) {
		if (notEmpty(start) && notEmpty(end)) {
			if (appendTime) {
				start = start.toString() + " 00:00:00";
				end = end.toString() + " 23:59:59";
			}
			for (StringBuilder sql : temps) {
				sql.append(condition);
			}
			for (int i = 0; i < num; i++) {
				args.add(start);
				args.add(end);
			}
		}
		return this;
	}

	public void orderBy(int index, String order) {
		if (temps.size() > index)
			temps.get(index).append(" ").append(order);
		else
			throw new IndexOutOfBoundsException("Can not found the index '" + index + "'");
	}

	private static boolean notEmpty(Object arg) {
		return arg != null && arg.toString().length() > 0;
	}

	public String getSql(int index) {
		if (temps.size() > index)
			return temps.get(index).toString();
		throw new IndexOutOfBoundsException("Can not found the index '" + index + "'");
	}

	public Object[] getArgs() {
		return this.args.toArray();
	}

}
