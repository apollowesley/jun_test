package com.hibernate.voDao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * CatchException entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hibernate.voDao.CatchException
 * @author MyEclipse Persistence Tools
 */
public class CatchExceptionDAO extends BaseHibernateDAO implements DAO<CatchException> {
	private static final Logger log = LoggerFactory.getLogger(CatchExceptionDAO.class);
	// property constants
	public static final String EXCEPTION = "exception";

	@Override
	public void save(CatchException transientInstance) {
		log.debug("saving CatchException instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	@Override
	public void delete(CatchException persistentInstance) {
		log.debug("deleting CatchException instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public CatchException findById(java.lang.Integer id) {
		log.debug("getting CatchException instance with id: " + id);
		try {
			CatchException instance = (CatchException) getSession().get("com.hibernate.voDao.CatchException", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(CatchException instance) {
		log.debug("finding CatchException instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.CatchException").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding CatchException instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from CatchException as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByException(Object exception) {
		return findByProperty(EXCEPTION, exception);
	}

	@Override
	public List findAll() {
		log.debug("finding all CatchException instances");
		try {
			String queryString = "from CatchException order by id";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public CatchException merge(CatchException detachedInstance) {
		log.debug("merging CatchException instance");
		try {
			CatchException result = (CatchException) getSession().merge(detachedInstance);
			getSession().flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(CatchException instance) {
		log.debug("attaching dirty CatchException instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(CatchException instance) {
		log.debug("attaching clean CatchException instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}