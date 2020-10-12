package com.hibernate.voDao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * BillsTopic entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hibernate.voDao.BillsTopic
 * @author MyEclipse Persistence Tools
 */
public class BillsTopicDAO extends BaseHibernateDAO implements DAO<BillsTopic> {
	private static final Logger log = LoggerFactory.getLogger(BillsTopicDAO.class);
	// property constants
	public static final String BTPNAME = "btpname";
	public static final String UID = "uid";
	public static final String USERCOUNT = "usercount";
	public static final String TOPICCOUNT = "topiccount";

	@Override
	public void save(BillsTopic transientInstance) {
		log.debug("saving BillsTopic instance");
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
	public void delete(BillsTopic persistentInstance) {
		log.debug("deleting BillsTopic instance");
		try {
			persistentInstance = (BillsTopic) getSession().load(BillsTopic.class, persistentInstance.getBtpid());
			getSession().delete(persistentInstance);
			getSession().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public BillsTopic findById(java.lang.Integer id) {
		log.debug("getting BillsTopic instance with id: " + id);
		try {
			BillsTopic instance = (BillsTopic) getSession().get("com.hibernate.voDao.BillsTopic", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(BillsTopic instance) {
		log.debug("finding BillsTopic instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.BillsTopic").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding BillsTopic instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from BillsTopic as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBtpname(Object btpname) {
		return findByProperty(BTPNAME, btpname);
	}

	public List findByUid(Object uid) {
		return findByProperty(UID, uid);
	}

	public List findByUsercount(Object usercount) {
		return findByProperty(USERCOUNT, usercount);
	}

	public List findByTopiccount(Object topiccount) {
		return findByProperty(TOPICCOUNT, topiccount);
	}

	@Override
	public List findAll() {
		log.debug("finding all BillsTopic instances");
		try {
			String queryString = "from BillsTopic";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public BillsTopic merge(BillsTopic detachedInstance) {
		log.debug("merging BillsTopic instance");
		try {
			BillsTopic result = (BillsTopic) getSession().merge(detachedInstance);
			getSession().flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(BillsTopic instance) {
		log.debug("attaching dirty BillsTopic instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(BillsTopic instance) {
		log.debug("attaching clean BillsTopic instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/**
	 * 查找所有的
	 * 
	 * @return
	 * @createTime 2014-5-1 上午2:28:02
	 */
	public List findAllName() {
		log.debug("finding all BillsTopic instances");
		try {
			String queryString = "select a.btpname from bills_topic a";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public BillsTopic findByName(String value) {
		log.debug("finding BillsTopic instance with Name: " + value + ", value: " + value);
		try {
			String queryString = "from BillsTopic as model where model." + BTPNAME + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return (BillsTopic) queryObject.list().get(0);
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List getThreeHotTopic() {
		try {
			String queryString = "select btpid,btpname,uid,btptime,btplasttime,usercount,topiccount from bills_topic t where tophot>0 order BY tophot desc limit 5";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}