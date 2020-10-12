package org.nature.framework.helper;

import java.util.List;

import org.nature.framework.core.NatureService;
import org.nature.framework.db.DbIdentifyer;
import org.nature.framework.db.MsSqlTableAnalyzer;
import org.nature.framework.db.MySqlTableAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableAutoCreateHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(TableAutoCreateHelper.class);

	public static void initDbTable() {
		if (ConfigHelper.getDbAutocreate()) {
			NatureService natureService = (NatureService) ServiceHelper.getService(NatureService.class);
			if (DbIdentifyer.isMySql()) {
				List<String> sqlList = MySqlTableAnalyzer.getSqlList();
				for (String sql : sqlList) {
					natureService.excute(sql);
				}
			} else if (DbIdentifyer.isMsSql()) {
				List<String> sqlList = MsSqlTableAnalyzer.getSqlList();
				for (String sql : sqlList) {
					natureService.excute(sql);
				}
			}
		}
	}
}
