package com.hibernate.voDao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Version entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hibernate.voDao.Version
 * @author MyEclipse Persistence Tools
 */
public class VersionDAO extends BaseHibernateDAO implements DAO<Version> {
	private static final Logger log = LoggerFactory.getLogger(VersionDAO.class);
	// property constants
	public static final String VERSION = "Version";

	@Override
	public void save(Version transientInstance) {
		log.debug("saving Version instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	@Override
	public void delete(Version persistentInstance) {
		log.debug("deleting Version instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public Version findById(java.lang.Integer id) {
		log.debug("getting Version instance with id: " + id);
		try {
			Version instance = (Version) getSession().get("com.hibernate.voDao.Version", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(Version instance) {
		log.debug("finding Version instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.Version").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Version instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Version as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByVersion(Object Version) {
		return findByProperty(VERSION, Version);
	}

	@Override
	public List findAll() {
		log.debug("finding all Version instances");
		try {
			String queryString = "from Version";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public Version merge(Version detachedInstance) {
		log.debug("merging Version instance");
		try {
			Version result = (Version) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(Version instance) {
		log.debug("attaching dirty Version instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(Version instance) {
		log.debug("attaching clean Version instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}