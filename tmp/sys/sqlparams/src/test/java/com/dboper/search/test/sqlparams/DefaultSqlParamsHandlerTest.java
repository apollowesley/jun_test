package com.dboper.search.test.sqlparams;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.dboper.search.sqlparams.DefaultSqlParamsHandler;
import com.dboper.search.sqlparams.SqlParamsParseResult;

public class DefaultSqlParamsHandlerTest {
	
	private DefaultSqlParamsHandler defaultSqlParamsHandler;
	
	@Before
	public void init(){
		defaultSqlParamsHandler=new DefaultSqlParamsHandler();
	}

	@Test
	public void testNormal(){
		Map<String,Object> params=new HashMap<String,Object>();
		params.put("a.name","lg");
		params.put("b.age@>=",2);
		System.out.println("--------------------------------------");
		System.out.println("testNormal:"+defaultSqlParamsHandler.getSqlWhereParams(params));
		SqlParamsParseResult sqlParamsParseResult=defaultSqlParamsHandler.getSqlWhereParamsResult(params);
		System.out.println("testNormal:"+sqlParamsParseResult.getBaseWhereSql());
		System.out.println("testNormal:"+sqlParamsParseResult.getArguments());
	}
	
	@Test
	public void testAndOr(){
		Map<String,Object> params=new HashMap<String,Object>();
		Map<String,Object> sonParams=new HashMap<String,Object>();
		sonParams.put("b.name","李四");
		sonParams.put("c.age@<",4);
		
		params.put("a.name","lg");
		params.put("$or",sonParams);
		System.out.println("--------------------------------------");
		System.out.println("testAndOr:"+defaultSqlParamsHandler.getSqlWhereParams(params));
		SqlParamsParseResult sqlParamsParseResult=defaultSqlParamsHandler.getSqlWhereParamsResult(params);
		System.out.println("testAndOr:"+sqlParamsParseResult.getBaseWhereSql());
		System.out.println("testAndOr:"+sqlParamsParseResult.getArguments());
	}
	
	@Test
	public void testAndOrComplex(){
		Map<String,Object> params=new HashMap<String,Object>();
		Map<String,Object> sonParams=new HashMap<String,Object>();
		Map<String,Object> sonSonParams=new HashMap<String,Object>();
		
		sonSonParams.put("d.name","王五");
		sonSonParams.put("e.age@>=",12);
		
		sonParams.put("b.name","李四");
		sonParams.put("c.age@<",4);
		sonParams.put("$and",sonSonParams);
		
		params.put("a.name","lg");
		params.put("$or",sonParams);
		System.out.println("--------------------------------------");
		System.out.println("testAndOrComplex:"+defaultSqlParamsHandler.getSqlWhereParams(params));
		SqlParamsParseResult sqlParamsParseResult=defaultSqlParamsHandler.getSqlWhereParamsResult(params);
		System.out.println("testAndOrComplex:"+sqlParamsParseResult.getBaseWhereSql());
		System.out.println("testAndOrComplex:"+sqlParamsParseResult.getArguments());
	}
	
	@Test
	public void testTwoColumnCompare(){
		Map<String,Object> params=new HashMap<String,Object>();
		
		params.put("a.age@col_>","b.age");
		
		System.out.println("--------------------------------------");
		System.out.println("testTwoColumnCompare:"+defaultSqlParamsHandler.getSqlWhereParams(params));
		SqlParamsParseResult sqlParamsParseResult=defaultSqlParamsHandler.getSqlWhereParamsResult(params);
		System.out.println("testTwoColumnCompare:"+sqlParamsParseResult.getBaseWhereSql());
		System.out.println("testTwoColumnCompare:"+sqlParamsParseResult.getArguments());
	}
}
