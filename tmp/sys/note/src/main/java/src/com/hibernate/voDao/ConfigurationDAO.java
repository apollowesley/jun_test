package com.hibernate.voDao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Configuration entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hibernate.voDao.Configuration
 * @author MyEclipse Persistence Tools
 */
public class ConfigurationDAO extends BaseHibernateDAO implements DAO<Configuration> {
	private static final Logger log = LoggerFactory.getLogger(ConfigurationDAO.class);
	// property constants
	public static final String CVALUE = "cvalue";

	@Override
	public void save(Configuration transientInstance) {
		log.debug("saving Configuration instance");
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
	public void delete(Configuration persistentInstance) {
		log.debug("deleting Configuration instance");
		try {
			persistentInstance = (Configuration) getSession().load(Configuration.class, persistentInstance.getCkey());
			getSession().delete(persistentInstance);
			getSession().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Configuration findById(java.lang.String id) {
		log.debug("getting Configuration instance with id: " + id);
		try {
			Configuration instance = (Configuration) getSession().get("com.hibernate.voDao.Configuration", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(Configuration instance) {
		log.debug("finding Configuration instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.Configuration").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Configuration instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Configuration as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByCvalue(Object cvalue) {
		return findByProperty(CVALUE, cvalue);
	}

	@Override
	public List findAll() {
		log.debug("finding all Configuration instances");
		try {
			String queryString = "from Configuration";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public Configuration merge(Configuration detachedInstance) {
		log.debug("merging Configuration instance");
		try {
			Configuration result = (Configuration) getSession().merge(detachedInstance);
			getSession().flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(Configuration instance) {
		log.debug("attaching dirty Configuration instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(Configuration instance) {
		log.debug("attaching clean Configuration instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public Configuration findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}