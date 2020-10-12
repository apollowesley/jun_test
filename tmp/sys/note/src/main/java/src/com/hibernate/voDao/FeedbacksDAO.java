package com.hibernate.voDao;

import java.util.List;
import java.util.Map;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Feedbacks entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hibernate.voDao.Feedbacks
 * @author MyEclipse Persistence Tools
 */
public class FeedbacksDAO extends BaseHibernateDAO implements DAO<Feedbacks> {
	private static final Logger log = LoggerFactory.getLogger(FeedbacksDAO.class);
	// property constants
	public static final String USERNAME = "username";
	public static final String UID = "uid";
	public static final String CONTENTS = "contents";
	public static final String REPLYSTATUS = "replystatus";
	public static final String RUSERNAME = "rusername";
	public static final String RUID = "ruid";
	public static final String RCONTENTS = "rcontents";
	public static final String FDATE = "fdate";

	@Override
	public void save(Feedbacks transientInstance) {
		log.debug("saving Feedbacks instance");
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
	public void delete(Feedbacks persistentInstance) {
		log.debug("deleting Feedbacks instance");
		try {
			persistentInstance = (Feedbacks) getSession().load(Feedbacks.class, persistentInstance.getFid());
			getSession().delete(persistentInstance);
			getSession().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public Feedbacks findById(java.lang.Integer id) {
		log.debug("getting Feedbacks instance with id: " + id);
		try {
			Feedbacks instance = (Feedbacks) getSession().get("com.hibernate.voDao.Feedbacks", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(Feedbacks instance) {
		log.debug("finding Feedbacks instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.Feedbacks").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Feedbacks instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Feedbacks as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUsername(Object username) {
		return findByProperty(USERNAME, username);
	}

	public List findByUid(Object uid) {
		return findByProperty(UID, uid);
	}

	public List findByContents(Object contents) {
		return findByProperty(CONTENTS, contents);
	}

	public List findByReplystatus(Object replystatus) {
		return findByProperty(REPLYSTATUS, replystatus);
	}

	public List findByRusername(Object rusername) {
		return findByProperty(RUSERNAME, rusername);
	}

	public List findByRuid(Object ruid) {
		return findByProperty(RUID, ruid);
	}

	public List findByReplaycontents(Object rcontents) {
		return findByProperty(RCONTENTS, rcontents);
	}

	@Override
	public List findAll() {
		log.debug("finding all Feedbacks instances");
		try {
			String queryString = "from Feedbacks";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public Feedbacks merge(Feedbacks detachedInstance) {
		log.debug("merging Feedbacks instance");
		try {
			Feedbacks result = (Feedbacks) getSession().merge(detachedInstance);
			getSession().flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(Feedbacks instance) {
		log.debug("attaching dirty Feedbacks instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(Feedbacks instance) {
		log.debug("attaching clean Feedbacks instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List findAllByPage(int pageNumber, int numPerPage) {
		log.debug("finding all Feedbacks instances");
		try {
			String queryString = "from Feedbacks as model where model." + REPLYSTATUS + "=1 order by " + FDATE + " desc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setFirstResult(pageNumber * numPerPage);
			queryObject.setMaxResults(numPerPage);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public String findAllByPageCounts() {
		log.debug("finding all Feedbacks instances");
		try {
			String queryString = "select count(model.fid) as counts from Feedbacks as model where model." + REPLYSTATUS + "=1";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List list = queryObject.list();
			Map map = (Map) list.get(0);
			return map.get("counts").toString();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}