package com.hibernate.voDao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * JpushLog entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hibernate.voDao.JpushLog
 * @author MyEclipse Persistence Tools
 */
public class JpushLogDAO extends BaseHibernateDAO implements DAO<JpushLog> {
	private static final Logger log = LoggerFactory.getLogger(JpushLogDAO.class);
	// property constants
	public static final String SEND_JPUSH = "sendJpush";
	public static final String RESPONSE_CODE = "responseCode";
	public static final String RESPONSE_MESSAGE = "responseMessage";
	public static final String RETRY_TIMES = "retryTimes";
	public static final String RETRY_AGAIN = "retryAgain";

	@Override
	public void save(JpushLog transientInstance) {
		log.debug("saving JpushLog instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			getSession().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	@Override
	public void delete(JpushLog persistentInstance) {
		log.debug("deleting JpushLog instance");
		try {
			getSession().delete(persistentInstance);
			getSession().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public JpushLog findById(java.lang.Integer id) {
		log.debug("getting JpushLog instance with id: " + id);
		try {
			JpushLog instance = (JpushLog) getSession().get("com.hibernate.voDao.JpushLog", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(JpushLog instance) {
		log.debug("finding JpushLog instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.JpushLog").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding JpushLog instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from JpushLog as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySendJpush(Object sendJpush) {
		return findByProperty(SEND_JPUSH, sendJpush);
	}

	public List findByResponseCode(Object responseCode) {
		return findByProperty(RESPONSE_CODE, responseCode);
	}

	public List findByResponseMessage(Object responseMessage) {
		return findByProperty(RESPONSE_MESSAGE, responseMessage);
	}

	public List findByRetryTimes(Object retryTimes) {
		return findByProperty(RETRY_TIMES, retryTimes);
	}

	public List findByRetryAgain(Object retryAgain) {
		return findByProperty(RETRY_AGAIN, retryAgain);
	}

	@Override
	public List findAll() {
		log.debug("finding all JpushLog instances");
		try {
			String queryString = "from JpushLog";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public JpushLog merge(JpushLog detachedInstance) {
		log.debug("merging JpushLog instance");
		try {
			JpushLog result = (JpushLog) getSession().merge(detachedInstance);
			getSession().flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(JpushLog instance) {
		log.debug("attaching dirty JpushLog instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(JpushLog instance) {
		log.debug("attaching clean JpushLog instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}