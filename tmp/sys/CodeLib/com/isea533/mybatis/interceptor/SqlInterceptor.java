package com.isea533.mybatis.interceptor;

import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.log4j.Logger;

import com.easternie.records.servlet.SysVar;

@Intercepts( { @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class SqlInterceptor implements Interceptor
{
    // 日志对象
    protected static Logger logger = Logger.getLogger(SqlInterceptor.class);
    @Override
    public Object intercept(Invocation invocation) throws Throwable{
    	StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler);
		//获得sql
		String originalSql = (String) metaStatementHandler.getValue("delegate.boundSql.sql");
		File sqlFile = null;
		List<String> lines = null;
		Object param = metaStatementHandler.getValue("delegate.boundSql.parameterObject");
		synchronized (this) {
			
			sqlFile = new File(SysVar.WEB_PATH.concat("/sql/").concat(DateFormatUtils.format(new Date(), "yyyyMMdd")).concat("-sql.txt"));/*此处是构造sql文件名称，那个Constant.SQLFILE是自己配的一个常量内容比如为：d:\\，可以随便写*/
			lines = new ArrayList<String>();
			lines.add("==============SQL输出："+DateUtil.formatDate(new Date(), DateUtil.DATE_FORMAT_TIME_T)+"===============");
			String sql = originalSql.replaceAll("\n", "").replaceAll("\t", "").replaceAll(" +", " ");
			logger.debug("\n\n==============SQL输出===============");
			logger.debug(sql);
			
			lines.add(sql);
			if(param instanceof HashMap){
				JSONObject jsonObject = JSONObject.fromObject(param);
				lines.add(jsonObject.toString());
				logger.debug(jsonObject.toString());
			}
			else if(param instanceof String){
				lines.add(param.toString());
				logger.debug(param.toString());
			}
			else if(param instanceof Number){
				lines.add(String.valueOf(param));
				logger.debug(String.valueOf(param));
			}else if(param instanceof Object){
				JSONObject jsonObject = JSONObject.fromObject(param);
				lines.add(jsonObject.toString());
				logger.debug(jsonObject.toString());
			}
			else {
				lines.add(String.valueOf(param));
				logger.debug(String.valueOf(param));
			}
			logger.debug("\n\n");
			FileWriter writer = null;
			try {
				writer = new FileWriter(sqlFile,true);
				IOUtils.writeLines(lines, null, writer);
			} catch (Exception e) {} 
			  finally{
				try {
					writer.close();
				} catch (Exception e2) {}
			}
			
		}
		return invocation.proceed();
    }

    @Override
    public Object plugin(Object target){
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties arg0) { }

}