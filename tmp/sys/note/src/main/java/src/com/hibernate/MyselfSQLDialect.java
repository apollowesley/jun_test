package com.hibernate;

import java.sql.Types;

import org.hibernate.Hibernate;
import org.hibernate.dialect.MySQLDialect;

public class MyselfSQLDialect extends MySQLDialect {

	public MyselfSQLDialect() {
		super();
		// very important, mapping char(n) to String
		registerHibernateType(Types.CHAR, Hibernate.STRING.getName());
		registerHibernateType(Types.LONGVARCHAR, Hibernate.TEXT.getName());
	}

}
/* 这个方法非常好 */
