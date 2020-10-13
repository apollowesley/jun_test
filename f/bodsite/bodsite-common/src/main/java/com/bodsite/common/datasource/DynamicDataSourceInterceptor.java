package com.bodsite.common.datasource;

import java.util.Properties;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * @Description: mybatis plugin 拦截器-设置数据源
 * @author bod
 * @date
 *
 */
@Intercepts({
		@Signature(method = "query", type = Executor.class, args = { MappedStatement.class, Object.class,
				RowBounds.class, ResultHandler.class }), // method：方法名，type：类，args:方法参数
		@Signature(method = "update", type = Executor.class, args = { MappedStatement.class, Object.class }) })
public class DynamicDataSourceInterceptor implements Interceptor {

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// 是否有事务
		boolean synchronizationActive = TransactionSynchronizationManager.isSynchronizationActive();
		if (!synchronizationActive) {
			Object[] args = invocation.getArgs();
			MappedStatement ms = (MappedStatement) args[0];
			if(ms.getSqlCommandType().equals(SqlCommandType.SELECT)){//查询
				  //!selectKey 为自增id查询主键(SELECT LAST_INSERT_ID() )方法，使用主库
                if(ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                	DataSourceHandler.setMaster();
                }else{
                	DataSourceHandler.setSlave();
                }
			}else{//其他
				DataSourceHandler.setMaster();
			}
		}
		Object result = invocation.proceed();
		DataSourceHandler.DataSoruceClean();
		return result;
	}

	@Override
	public Object plugin(Object target) {
		if (target instanceof Executor) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {

	}

}
