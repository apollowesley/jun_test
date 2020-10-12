package org.nature4j.framework.helper;

import java.util.List;

import org.nature4j.framework.core.NatureService;
import org.nature4j.framework.db.Db;
import org.nature4j.framework.db.DbIdentifyer;
import org.nature4j.framework.db.MsSqlTableAnalyzer;
import org.nature4j.framework.db.MySqlTableAnalyzer;
import org.nature4j.framework.db.SqliteTableAnalyzer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TableAutoCreateHelper {
	private static Logger LOGGER = LoggerFactory.getLogger(TableAutoCreateHelper.class);

	public static void initDbTable() {
		if (ConfigHelper.getDbAutocreate()) {
			final String[] dbs = ConfigHelper.getDb();
			for (final String db : dbs) {
				String jdbcDriver = DatabaseHelper.use(db).jdbcDriver;
				Db natureDb = new Db() {
					public String name() {
						return db;
					}
				};

				NatureService natureService = (NatureService) ServiceHelper.getService(NatureService.class);
				if (DbIdentifyer.isMySql(jdbcDriver)) {
					List<String> sqlList = MySqlTableAnalyzer.use(natureDb).getSqlList();
					for (String sql : sqlList) {
						natureService.excute(natureDb, sql);
					}
				} else if (DbIdentifyer.isMsSql(jdbcDriver)) {
					List<String> sqlList = MsSqlTableAnalyzer.use(natureDb).getSqlList();
					for (String sql : sqlList) {
						natureService.excute(natureDb, sql);
					}
				} else if (DbIdentifyer.isSqlite(jdbcDriver)) {
					List<String> sqlList = SqliteTableAnalyzer.use(natureDb).getSqlList();
					for (String sql : sqlList) {
						natureService.excute(natureDb, sql);
					}
				}
			}
		}
	}

}
