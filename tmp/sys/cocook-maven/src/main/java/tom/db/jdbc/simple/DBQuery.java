package tom.db.jdbc.simple;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tom.db.jdbc.Query;
import tom.kit.StringUtil;


public class DBQuery implements Query {

	private StringBuffer sql = new StringBuffer();
	
	
	private List<Object> params = new ArrayList<Object>();

	public DBQuery(String execSql) {
		sql = sql.append(execSql);
	}
	
	public DBQuery(StringBuffer sql){
		this.sql = sql;
	}
	
	
	@Override
	public Query setParams(Object obj) {
		params.add(obj);
		return this;
	}
	@Override
	public Query setParams(String obj) {
		return setParams((Object)obj);
	}

	@Override
	public Query setParams(int obj) {
		return setParams((Object)obj);
	}

	@Override
	public Query setParams(double obj) {
		return setParams((Object)obj);
	}

	@Override
	public Query setParams(float obj) {
		return setParams((Object)obj);
	}

	@Override
	public Query setParams(long obj) {
		return setParams((Object)obj);
	}

	@Override
	public Query setParams(boolean obj) {
		return setParams((Object)obj);
	}

	@Override
	public Query setParams(Timestamp obj) {
		return setParams((Object)obj);
	}

	@Override
	public Query setParams(Date obj) {
		return setParams((Object)obj);
	}

	@Override
	public Query setParams(BigDecimal obj) {
		return setParams((Object)obj);
	}
	
	@Override
	public Object[] getParams() {
		return params.toArray();
	}
	
	@Override
	public String getSql() {
		return sql.toString();
	}
	
	@Override
	public Query upset(String upset) {
		if(params.size()> 0){
			sql.append(",");
		}
		sql.append(upset);
		return this;
	}
	
	@Override
	public Query where(String where) {
		sql.append(" WHERE ").append(where);
		return this;
	}

	@Override
	public Query and(String where) {
		sql.append(" AND ").append(where);
		return this;
	}

	@Override
	public Query or(String where) {
		sql.append(" OR ").append(where);
		return this;
	}

	@Override
	public Query asc(String where) {
		sql.append(" ORDER BY ").append(where).append(" ASC ");
		return this;
	}

	@Override
	public Query desc(String where) {
		sql.append(" ORDER BY ").append(where).append(" DESC ");
		return this;
	}

	@Override
	public Query between(Object... result) {
		if (result == null || result.length != 2) {
			throw new RuntimeException(" error between parameter... ");
		}
		sql.append(" BETWEEN ");
		for (int i = 0; i < result.length; i++) {
			if (i > 0) {
				sql.append(" AND ");
			}
			sql.append(format(result[i]));
		}
		return this;
	}

	@Override
	public Query eq(Object result) {
		sql.append(" = ").append(format(result));
		return this;
	}

	@Override
	public Query notEq(Object result) {
		sql.append(" <> ").append(format(result));
		return this;
	}

	@Override
	public Query ge(Object result) {
		sql.append(" >= ").append(format(result));
		return this;
	}

	@Override
	public Query gt(Object result) {
		sql.append(" > ").append(format(result));
		return this;
	}

	@Override
	public Query in(Object... result) {
		sql.append(" IN (");
		for (int i = 0; i < result.length; i++) {
			if (i > 0) {
				sql.append(",");
			}
			sql.append(format(result[i]));
		}
		sql.append(" )");
		return this;
	}

	@Override
	public Query isNotNull() {
		sql.append(" IS NOT NULL ");
		return this;
	}

	@Override
	public Query isNull() {
		sql.append(" IS NULL ");
		return this;
	}

	@Override
	public Query le(Object result) {
		sql.append(" <= ").append(format(result));
		return this;
	}

	@Override
	public Query lt(Object result) {
		sql.append(" < ").append(format(result));
		return this;
	}

	@Override
	public Query like(String result) {
		sql.append(" LIKE ").append(format(result));
		return this;
	}

	@Override
	public Query not() {
		sql.append(" NOT ");
		return this;
	}
	
	@Override
	public Query and() {
		sql.append(" AND ");
		return this;
	}

	@Override
	public Query add(String _sql) {
		sql.append(_sql);
		return this;
	}
	
	@Override
	public Query append(String _sql) {
		sql.append(_sql);
		return this;
	}

	private Object format(Object obj) {
		if("?".equals(String.valueOf(obj))){
			return obj;
		}
		if (obj instanceof String) {
			obj = "'" + StringUtil.escapeSql(obj.toString()) + "'";
		} else if (obj instanceof Timestamp) {
			obj = "'" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj) + "'";
		} else if (obj instanceof Date) {
			obj = "'" + new SimpleDateFormat("yyyy-MM-dd").format(obj) + "'";
		}
		return obj;
	}

	

	public static void main(String[] args) {
		long a = System.currentTimeMillis();
		
//		Query query =  new DBQuery(new StringBuffer("select b.*, name n, path p from fun b "));
			
	
//		Query query = new DBQuery(Fun.class);
		Query query =  new DBQuery("select b.*, name n, path p from fun b ")
		.where("id").isNotNull()
		.and("sort").in("1",null)
		.and("name").ge("?").setParams("panmg")
		.and("name").gt("tomsun")
		.and("name").le("panmg")
		.and("name").lt("tomsun")
		.and("name").eq("tomsun")
		.and("name").notEq("0001")
		.and("name").not().like("%d%")
		.and("name").not().between("panmg","tomsun")
		.and("name").eq("tomsun")
		.desc("sort");
		
		System.out.println(query.getSql());
		
		query = new DBQuery("update suse set");
		query.upset(" a =?").setParams(1);
		query.upset(" b=?").setParams(1);
		query.upset(" c=?").setParams(1);
		query.upset(" where=?").setParams(1);
		
		System.out.println(query.getSql());
	}



	

	

	
}
