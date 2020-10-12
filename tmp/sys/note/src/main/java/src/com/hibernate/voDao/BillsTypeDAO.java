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
 * BillsType entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hibernate.voDao.BillsType
 * @author MyEclipse Persistence Tools
 */
public class BillsTypeDAO extends BaseHibernateDAO implements DAO<BillsType> {
	private static final Logger log = LoggerFactory.getLogger(BillsTypeDAO.class);
	// property constants
	public static final String BTYPENAME = "btypename";
	public static final String BTYPEUSERID = "btypeuserid";

	@Override
	public void save(BillsType transientInstance) {
		log.debug("saving BillsType instance");
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
	public void delete(BillsType persistentInstance) {
		log.debug("deleting BillsType instance");
		try {
			persistentInstance = (BillsType) getSession().load(BillsType.class, persistentInstance.getBtypeid());
			getSession().delete(persistentInstance);
			getSession().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public BillsType findById(java.lang.Integer id) {
		log.debug("getting BillsType instance with id: " + id);
		try {
			BillsType instance = (BillsType) getSession().get("com.hibernate.voDao.BillsType", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(BillsType instance) {
		log.debug("finding BillsType instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.BillsType").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding BillsType instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from BillsType as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBtypename(Object btypename) {
		return findByProperty(BTYPENAME, btypename);
	}

	public List findByBtypeuserid(Object btypeuserid) {
		return findByProperty(BTYPEUSERID, btypeuserid);
	}

	@Override
	public List findAll() {
		log.debug("finding all BillsType instances");
		try {
			String queryString = "from BillsType";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public BillsType merge(BillsType detachedInstance) {
		log.debug("merging BillsType instance");
		try {
			BillsType result = (BillsType) getSession().merge(detachedInstance);
			getSession().flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(BillsType instance) {
		log.debug("attaching dirty BillsType instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(BillsType instance) {
		log.debug("attaching clean BillsType instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/** 只是用来检测是否存在，如果检测到第一条，就停止 */
	public List findByNameTestIsExist(String propertyName, Object value) {
		log.debug("finding BillsType instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from BillsType as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			queryObject.setFirstResult(0);
			queryObject.setMaxResults(1);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/** 获取当前用户的所有账单类型 */
	public List findByUserId(Object uid) {
		log.debug("finding BillsType instance with uid");
		try {
			String queryString = "SELECT DISTINCT a.* FROM bills_type a,bills b WHERE (a.btypeuserid =" + uid
					+ " or a.btypeuserid=0)  AND NOT EXISTS (SELECT 1 FROM bills WHERE a.btypeid=btype and bbetravelleader=1 and a.btypeuserid=" + uid + ")";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/** 获取当前用户的查找账单类型名称对应的id */
	public List findByUserIdandtypename(Object uid, String typename) {
		log.debug("finding BillsType instance with uid");
		try {
			String queryString = "SELECT DISTINCT a.* FROM bills_type a WHERE (a.btypeuserid =" + uid + " or a.btypeuserid=0)  AND a.btypename like '" + typename + "'";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.aliasToBean(BillsType.class));
			queryObject.setFirstResult(0);
			queryObject.setMaxResults(1);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}