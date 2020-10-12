package org.nature.framework.db;

import org.nature.framework.bean.Page;
import org.nature.framework.bean.TableBean;
import org.nature.framework.core.NatureMap;

public interface DialectIntferface {

	String tranformInsertSql(NatureMap natureMap, Class<? extends NatureMap> cls, TableBean tableBean);

	Object[] tranformInsertSqlWithParams(NatureMap natureMap, Class<? extends NatureMap> cls, TableBean tableBean);

	String tranformUpdateSql(NatureMap natureMap);

	Object[] tranformUpdateSqlWithParams(NatureMap natureMap);

	String tranformDeleteSql(NatureMap natureMap);

	String tranformByIdSql(NatureMap natureMap);

	String tranformPageSql(Page page, String sql);

	String tranformCntSql(String sql);

}