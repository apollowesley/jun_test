package com.hibernate.voDao.admin;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hibernate.voDao.BaseHibernateDAO;
import com.hibernate.voDao.DAO;

/**
 * A data access object (DAO) providing persistence and search support for
 * Sysmenu entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hibernate.voDao.admin.Sysmenu
 * @author MyEclipse Persistence Tools
 */
public class SysmenuDAO extends BaseHibernateDAO implements DAO<Sysmenu> {
	private static final Logger log = LoggerFactory.getLogger(SysmenuDAO.class);
	// property constants
	public static final String SELFKEY = "selfkey";
	public static final String PARENTKEY = "parentkey";
	public static final String NAME = "name";
	public static final String PRIORITY = "priority";
	public static final String ORDER = "order";
	public static final String URL = "url";

	@Override
	public void save(Sysmenu transientInstance) {
		log.debug("saving Sysmenu instance");
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
	public void delete(Sysmenu persistentInstance) {
		log.debug("deleting Sysmenu instance");
		try {
			persistentInstance = (Sysmenu) getSession().load(Sysmenu.class, persistentInstance.getId());
			getSession().delete(persistentInstance);
			getSession().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public Sysmenu findById(java.lang.Integer id) {
		log.debug("getting Sysmenu instance with id: " + id);
		try {
			Sysmenu instance = (Sysmenu) getSession().get("com.hibernate.voDao.admin.Sysmenu", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(Sysmenu instance) {
		log.debug("finding Sysmenu instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.admin.Sysmenu").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Sysmenu instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Sysmenu as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findBySelfkey(Object selfkey) {
		return findByProperty(SELFKEY, selfkey);
	}

	public List findByParentkey(Object parentkey) {
		return findByProperty(PARENTKEY, parentkey);
	}

	public List findByName(Object name) {
		return findByProperty(NAME, name);
	}

	public List findByPriority(Object priority) {
		return findByProperty(PRIORITY, priority);
	}

	public List findByOrder(Object order) {
		return findByProperty(ORDER, order);
	}

	public List findByUrl(Object url) {
		return findByProperty(URL, url);
	}

	@Override
	public List findAll() {
		log.debug("finding all Sysmenu instances");
		try {
			String queryString = "from Sysmenu";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public Sysmenu merge(Sysmenu detachedInstance) {
		log.debug("merging Sysmenu instance");
		try {
			Sysmenu result = (Sysmenu) getSession().merge(detachedInstance);
			getSession().flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(Sysmenu instance) {
		log.debug("attaching dirty Sysmenu instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(Sysmenu instance) {
		log.debug("attaching clean Sysmenu instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List getH() {
		log.debug("finding horizontal Sysmenu instances");
		try {
			String queryString = "from Sysmenu a where a.parentkey='0'";
			Query queryObject = getSession().createQuery(queryString);
			// queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	public List getV(String parentName) {
		log.debug("finding horizontal Sysmenu instances");
		try {
			String queryString = "from Sysmenu a where a.parentkey=(select selfkey from Sysmenu where id='" + parentName + "')";
			Query queryObject = getSession().createQuery(queryString);
			// queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}
}