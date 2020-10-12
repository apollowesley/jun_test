package org.nature.framework.db;

public class DialectFactory {

	public static DialectIntferface getInstance() {
		if (DbIdentifyer.isMySql()) {
			return MySqlDialect.getInstance();
		} else if (DbIdentifyer.isMsSql()) {
			return MsSqlDialect.getInstance();
		} else {
			return null;
		}
	}
}
