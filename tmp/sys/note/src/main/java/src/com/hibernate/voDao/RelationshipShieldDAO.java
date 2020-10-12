package com.hibernate.voDao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * RelationshipShield entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hibernate.voDao.RelationshipShield
 * @author MyEclipse Persistence Tools
 */
public class RelationshipShieldDAO extends BaseHibernateDAO implements DAO<RelationshipShield> {
	private static final Logger log = LoggerFactory.getLogger(RelationshipShieldDAO.class);
	// property constants
	public static final String USER_ID1 = "userId1";
	public static final String USER_ID2 = "userId2";
	public static final String TYPE = "type";

	@Override
	public void save(RelationshipShield transientInstance) {
		log.debug("saving RelationshipShield instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	@Override
	public void delete(RelationshipShield persistentInstance) {
		log.debug("deleting RelationshipShield instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public RelationshipShield findById(java.lang.Integer id) {
		log.debug("getting RelationshipShield instance with id: " + id);
		try {
			RelationshipShield instance = (RelationshipShield) getSession().get("com.hibernate.voDao.RelationshipShield", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(RelationshipShield instance) {
		log.debug("finding RelationshipShield instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.RelationshipShield").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding RelationshipShield instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from RelationshipShield as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUserId1(Object userId1) {
		return findByProperty(USER_ID1, userId1);
	}

	public List findByUserId2(Object userId2) {
		return findByProperty(USER_ID2, userId2);
	}

	public List findByType(Object type) {
		return findByProperty(TYPE, type);
	}

	@Override
	public List findAll() {
		log.debug("finding all RelationshipShield instances");
		try {
			String queryString = "from RelationshipShield";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public RelationshipShield merge(RelationshipShield detachedInstance) {
		log.debug("merging RelationshipShield instance");
		try {
			RelationshipShield result = (RelationshipShield) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(RelationshipShield instance) {
		log.debug("attaching dirty RelationshipShield instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(RelationshipShield instance) {
		log.debug("attaching clean RelationshipShield instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}