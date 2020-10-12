package com.dboper.search.sqlparams.parser;

public class DefaultColumnSqlParamsParser extends AbstractColumnSqlParamsParser{

	public DefaultColumnSqlParamsParser(){
		setOpers(new String[]{"col_=","col_!=","col_>","col_<","col_>=","col_<="});
	}
	
	/**
	 * 只需更改oper，去掉col_前缀
	 */
	@Override
	protected SqlParamsParseItemResult doParams(String key, Object value,
			String oper) {
		return new SqlParamsParseItemResult(key,oper.substring("col_".length()),value);
	}

}
