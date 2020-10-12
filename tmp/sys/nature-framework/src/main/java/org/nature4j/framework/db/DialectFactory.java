package org.nature4j.framework.db;

public class DialectFactory {

	public static DialectIntferface getInstance(String jdbcDriver) {
		if (DbIdentifyer.isMySql(jdbcDriver)) {
			return MySqlDialect.getInstance();
		} else if (DbIdentifyer.isMsSql(jdbcDriver)) {
			return MsSqlDialect.getInstance();
		} else if (DbIdentifyer.isSqlite(jdbcDriver)) {
			return SqliteDialect.getInstance();
		} else if (DbIdentifyer.isOracle(jdbcDriver)){
			return null;
		}
		return null;
	}
}
