package com.freedom.mysql.myrwsplit.plugin;

import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;

import com.freedom.mysql.myrwsplit.helper.LoggerHelper;
import com.freedom.mysql.myrwsplit.helper.ThreadLocalHelper;

@Intercepts(value = {})
public class StatementHandlerInterceptor implements Interceptor {
	private static LoggerHelper LOGGER = LoggerHelper.getLogger(StatementHandlerInterceptor.class);

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		Object result = invocation.proceed();
		return result;
	}

	@Override
	public Object plugin(Object target) {
		// 仅仅处理RoutingStatementHandler
		// 仅仅为了在获取Connection之前获得sql模板
		// 执行到这里的时候，sql已经之前生成了,所以，尽管使用
		//LOGGER.info("StatementHandlerInterceptor.plugin--->" + target);
		if (target instanceof StatementHandler) {
			// LOGGER.info("TTT ---> " + target);
			StatementHandler statementHandler = (StatementHandler) target;
			String sql = statementHandler.getBoundSql().getSql();
			LOGGER.info("sql ---> " + sql);
			ThreadLocalHelper.BoundSqlThreadLocal.set(sql);
			//new Exception().printStackTrace();
		}
		return target;
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
