package org.nature4j.framework.db;

import java.util.List;

import org.nature4j.framework.bean.Page;
import org.nature4j.framework.bean.TableBean;
import org.nature4j.framework.core.NatureMap;

public interface DialectIntferface {

	String tranformInsertSql(NatureMap natureMap, Class<? extends NatureMap> cls, TableBean tableBean);

	Object[] tranformInsertSqlWithParams(NatureMap natureMap, Class<? extends NatureMap> cls, TableBean tableBean);

	String tranformUpdateSql(NatureMap natureMap);

	Object[] tranformUpdateSqlWithParams(NatureMap natureMap);

	String tranformDeleteSql(NatureMap natureMap);

	String[] tranformByIdSqlWithParam(NatureMap natureMap);

	String tranformPageSql(Page page, String sql);

	String tranformCntSql(String sql);

	Object[] tranformInsertBatchSqlWithParams(List<? extends NatureMap> natureMaps, Class<? extends NatureMap> cls,
			TableBean tableBean);

}