package cn.coder.jdbc;

import java.util.List;

import javax.sql.DataSource;

import cn.coder.jdbc.core.EntityWrapper.SQLType;
import cn.coder.jdbc.mapper.DefaultResultMapper;
import cn.coder.jdbc.mapper.EntityResultMapper;
import cn.coder.jdbc.mapper.MulitResultMapper;
import cn.coder.jdbc.mapper.QueryResultMapper;
import cn.coder.jdbc.mapper.SingleResultMapper;
import cn.coder.jdbc.support.JSql;
import cn.coder.jdbc.support.MulitResult;
import cn.coder.jdbc.support.PageResult;
import cn.coder.jdbc.support.ResultMapper;

import static cn.coder.jdbc.util.ObjectUtils.mergeArray;

/**
 * SQL查询、执行工具类
 * 
 * @author YYDF
 *
 */
public final class SqlSession extends SqlSessionBase {

	public SqlSession(DataSource ds) {
		super(ds);
	}

	public boolean selectMulit(final MulitResult mr) {
		return execute(new MulitResultMapper(mr));
	}

	public <T> List<T> selectList(final Class<T> target, final JSql sql) {
		return selectList(target, sql.getSql(0), sql.getArgs());
	}

	public <T> List<T> selectList(final Class<T> target, final String sql, Object... array) {
		return execute(new QueryResultMapper<>(target, sql, array));
	}

	public <T> List<T> selectPage(final Class<T> target, final PageResult result, final JSql sql) {
		return selectPage(target, result, sql.getSql(0), sql.getSql(1), sql.getArgs());
	}

	public <T> List<T> selectPage(final Class<T> target, final PageResult result, final String fetchSql,
			final String countSql, Object... array) {
		result.setTotal(selectOne(Long.class, countSql, array));
		String fetchSql2 = fetchSql + " LIMIT ?,?";
		return selectList(target, fetchSql2, mergeArray(array, result.getStartRow(), result.getPageSize()));
	}

	public <T> T selectOne(final Class<T> target, final JSql sql) {
		return selectOne(target, sql.getSql(0), sql.getArgs());
	}

	public <T> T selectOne(final Class<T> target, final String sql, Object... array) {
		return execute(new SingleResultMapper<>(target, sql, array));
	}

	public boolean exist(final Object data) {
		return execute(new EntityResultMapper(data, SQLType.SELECT));
	}

	public boolean insert(final Object data) {
		return execute(new EntityResultMapper(data, SQLType.INSERT));
	}

	public boolean update(final Object data) {
		return execute(new EntityResultMapper(data, SQLType.UPDATE));
	}

	public boolean delete(final Object data) {
		return execute(new EntityResultMapper(data, SQLType.DELETE));
	}

	public int execute(final String sql, final Object... array) {
		return execute(new DefaultResultMapper(sql, array));
	}

	public <T> T execute(final ResultMapper<T> mapper) {
		return doExecute(mapper);
	}

}
