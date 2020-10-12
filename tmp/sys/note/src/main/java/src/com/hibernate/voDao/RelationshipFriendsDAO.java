package com.hibernate.voDao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * RelationshipFriends entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hibernate.voDao.RelationshipFriends
 * @author MyEclipse Persistence Tools
 */
public class RelationshipFriendsDAO extends BaseHibernateDAO implements DAO<RelationshipFriends> {
	private static final Logger log = LoggerFactory.getLogger(RelationshipFriendsDAO.class);
	// property constants
	public static final String USER_ID1 = "userId1";
	public static final String USER_ID2 = "userId2";
	public static final String STATUS = "status";

	@Override
	public void save(RelationshipFriends transientInstance) {
		log.debug("saving RelationshipFriends instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	@Override
	public void delete(RelationshipFriends persistentInstance) {
		log.debug("deleting RelationshipFriends instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public RelationshipFriends findById(java.lang.Integer id) {
		log.debug("getting RelationshipFriends instance with id: " + id);
		try {
			RelationshipFriends instance = (RelationshipFriends) getSession().get("com.hibernate.voDao.RelationshipFriends", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(RelationshipFriends instance) {
		log.debug("finding RelationshipFriends instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.RelationshipFriends").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding RelationshipFriends instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from RelationshipFriends as model where model." + propertyName + "= ?";
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

	public List findByStatus(Object status) {
		return findByProperty(STATUS, status);
	}

	@Override
	public List findAll() {
		log.debug("finding all RelationshipFriends instances");
		try {
			String queryString = "from RelationshipFriends";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public RelationshipFriends merge(RelationshipFriends detachedInstance) {
		log.debug("merging RelationshipFriends instance");
		try {
			RelationshipFriends result = (RelationshipFriends) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(RelationshipFriends instance) {
		log.debug("attaching dirty RelationshipFriends instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(RelationshipFriends instance) {
		log.debug("attaching clean RelationshipFriends instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

}