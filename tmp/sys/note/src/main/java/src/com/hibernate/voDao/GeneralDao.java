package com.hibernate.voDao;

import java.util.List;

import org.hibernate.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for Bills
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.hibernate.voDao.Bills
 * @author MyEclipse Persistence Tools
 */
public class GeneralDao extends BaseHibernateDAO implements GeneralDaoInterface {
	private static final Logger log = LoggerFactory.getLogger(GeneralDao.class);

	@Override
	public int executeUpdate(String sql) {
		log.debug("executeUpdate sql statement");
		int result;
		try {
			result = getSession().createSQLQuery(sql).executeUpdate();
			getSession().flush();
			log.debug("executeUpdate successful");
		} catch (RuntimeException re) {
			log.error("executeUpdate failed", re);
			throw re;
		}
		return result;
	}

	@Override
	public int executeUpdate(String sql, String... args) {
		log.debug("executeUpdate sql statement");
		int result;
		try {
			Query query = getSession().createSQLQuery(sql);
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
			result = query.executeUpdate();
			getSession().flush();
			log.debug("executeUpdate successful");
		} catch (RuntimeException re) {
			log.error("executeUpdate failed", re);
			throw re;
		}
		return result;
	}

	@Override
	public List getList(String sql, String... args) {
		log.debug("executeUpdate sql statement");
		List result;
		try {
			Query query = getSession().createSQLQuery(sql);
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
			result = query.list();
			getSession().flush();
			log.debug("executeUpdate successful");
		} catch (RuntimeException re) {
			log.error("executeUpdate failed", re);
			throw re;
		}
		return result;
	}

	@Override
	public List getListHql(String hql, String... args) {
		log.debug("executeUpdate sql statement");
		List result;
		try {
			Query query = getSession().createQuery(hql);
			for (int i = 0; i < args.length; i++) {
				query.setParameter(i, args[i]);
			}
			result = query.list();
			getSession().flush();
			log.debug("executeUpdate successful");
		} catch (RuntimeException re) {
			log.error("executeUpdate failed", re);
			throw re;
		}
		return result;
	}
}