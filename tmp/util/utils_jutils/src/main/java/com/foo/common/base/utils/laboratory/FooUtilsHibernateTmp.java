package com.foo.common.base.utils.laboratory;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.foo.common.base.utils.FooUtils;

public class FooUtilsHibernateTmp {
	private static final SessionFactory sessionFactory = null;

	public static SessionFactory buildSessionFactory() {
		try {
			// Create the SessionFactory from hibernate.cfg.xml
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.build();
			return new Configuration().configure().buildSessionFactory(
					serviceRegistry);
		} catch (Throwable ex) {
			// Make sure you log the exception, as it might be swallowed
			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public static void main1(String[] args) {
		System.out.println(FooUtils
				.toDateFromYear2Second("2013-04-19 12:00:00").after(
						FooUtils.toDateFromYear2Second("2013-04-19 23:00:00")));
	}

}
