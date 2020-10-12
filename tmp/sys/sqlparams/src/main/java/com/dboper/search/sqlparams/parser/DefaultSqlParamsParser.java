package com.dboper.search.sqlparams.parser;

public class DefaultSqlParamsParser extends AbstractSqlParamsParser{
	
	public DefaultSqlParamsParser(){
		setOpers(new String[]{"=","!=","is",">","<",">=","<=","like"});
	}

	/**
	 * 最简单的情况下，key value oper都不发生变化
	 */
	@Override
	protected SqlParamsParseItemResult doParams(String key, Object value, String oper) {
		return new SqlParamsParseItemResult(key, oper, value);
	}

}
