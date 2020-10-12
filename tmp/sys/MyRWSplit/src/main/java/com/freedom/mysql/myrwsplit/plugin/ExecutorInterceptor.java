package com.freedom.mysql.myrwsplit.plugin;

import java.util.Properties;

import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.BaseExecutor;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;

import com.freedom.mysql.myrwsplit.helper.LoggerHelper;
import com.freedom.mysql.myrwsplit.helper.ThreadLocalHelper;

import org.apache.ibatis.plugin.Intercepts;

/**
 * 数据库操作性能拦截器,记录耗时
 * 
 * @Intercepts定义Signature数组,因此可以拦截多个,但是只能拦截类型为： Executor ParameterHandler
 *                                              StatementHandler
 *                                              ResultSetHandler
 */
// 拦截BaseExecutor,这样，可以拦截各种类型的Executor实现
@Intercepts(value = {
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }),
		@Signature(type = Executor.class, method = "query", args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class }),
		@Signature(type = Executor.class, method = "update", args = { MappedStatement.class, Object.class })

})
public class ExecutorInterceptor implements Interceptor {
	private static LoggerHelper LOGGER = LoggerHelper.getLogger(ExecutorInterceptor.class);

	public ExecutorInterceptor() {
	}

	//
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 0)reset all
		ThreadLocalHelper.reset();
		LOGGER.info("清除了所有线程变量");
		// 1)get AutoCommit type
		BaseExecutor baseExecutor = (BaseExecutor) invocation.getTarget();//
		JdbcTransaction jdbcTransaction = (JdbcTransaction) baseExecutor.getTransaction();
		MetaObject metaObject = SystemMetaObject.forObject(jdbcTransaction);
		Object autoCommitObject = metaObject.getValue("autoCommmit");// 第1行完全是MyBatis作者笔误
		if (null == autoCommitObject) {
			autoCommitObject = metaObject.getValue("autoCommit");
		}
		LOGGER.info("autoCommit ---> " + (boolean) autoCommitObject);
		ThreadLocalHelper.AutoCommitThreadLocal.set((boolean) autoCommitObject);
		// 2)get sql type
		Object[] args = invocation.getArgs();
		MappedStatement ms = (MappedStatement) args[0];
		SqlCommandType sqlType = ms.getSqlCommandType();
		ThreadLocalHelper.SqlCommandTypeThreadLocal.set(sqlType);// save it to
																	// local
																	// thread
		LOGGER.info("set ThreadLocal sqlCommandType ---> " + sqlType);
		//
		//
		//
		// 3)continue to work
		long start = System.currentTimeMillis();
		Object result = invocation.proceed();
		long end = System.currentTimeMillis();
		// 4)sql audit
		long totalCost = end - start;
		long getConnectionCost = ThreadLocalHelper.FetchConnectionTimeThreadLocal.get();
		long realCost = totalCost - getConnectionCost;
		String sql = ThreadLocalHelper.BoundSqlThreadLocal.get();
		if (sql == null) {
			LOGGER.error("fail to get bound sql from current execution!!!");
		}
		// 5) log it,you can use MyEye-metrics-client to send it to http web
		// server
		// LOGGER.info("get Connection cost--->" + getConnectionCost + " ms");
		LOGGER.info("Sql Stats --->[" + sql + "] (" + (realCost) + ") ms");
		return result;
	}

	@Override
	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	@Override
	public void setProperties(Properties properties) {

	}

}