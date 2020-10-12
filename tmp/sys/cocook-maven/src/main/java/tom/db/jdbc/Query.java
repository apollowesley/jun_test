package tom.db.jdbc;

import java.math.BigDecimal;

public interface Query {

	public abstract Query setParams(String obj);

	public abstract Query setParams(int obj);

	public abstract Query setParams(double obj);

	public abstract Query setParams(float obj);

	public abstract Query setParams(long obj);

	public abstract Query setParams(boolean obj);

	public abstract Query setParams(java.sql.Timestamp obj);

	public abstract Query setParams(java.util.Date obj);

	public abstract Query setParams(BigDecimal obj);

	public abstract Query setParams(Object obj);

	public abstract Object[] getParams();
	
	public abstract Query upset(String upset);

	public abstract Query where(String where);

	public abstract Query and(String where);

	public abstract Query and();

	public abstract Query or(String where);

	public abstract Query not();

	public abstract Query in(Object... result);

	public abstract Query between(Object... result);

	public abstract Query eq(Object result);

	public abstract Query notEq(Object result);

	public abstract Query gt(Object result);

	public abstract Query ge(Object result);

	public abstract Query lt(Object result);

	public abstract Query le(Object result);

	public abstract Query isNull();

	public abstract Query isNotNull();

	public abstract Query like(String result);

	public abstract Query asc(String where);

	public abstract Query desc(String where);

	public abstract Query add(String sql);
	
	public abstract Query append(String _sql);

	public abstract String getSql();

}
