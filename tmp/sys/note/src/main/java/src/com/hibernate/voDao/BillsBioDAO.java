package com.hibernate.voDao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * BillsBio entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hibernate.voDao.BillsBio
 * @author MyEclipse Persistence Tools
 */
public class BillsBioDAO extends BaseHibernateDAO implements DAO<BillsBio> {
	private static final Logger log = LoggerFactory.getLogger(BillsBioDAO.class);
	// property constants
	public static final String BIONAME = "bioname";
	public static final String BSIGN = "bsign";

	@Override
	public void save(BillsBio transientInstance) {
		log.debug("saving BillsBio instance");
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
	public void delete(BillsBio persistentInstance) {
		log.debug("deleting BillsBio instance");
		try {
			persistentInstance = (BillsBio) getSession().load(BillsBio.class, persistentInstance.getBio());
			getSession().delete(persistentInstance);
			getSession().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public BillsBio findById(Short id) {
		log.debug("getting BillsBio instance with id: " + id);
		try {
			BillsBio instance = (BillsBio) getSession().get("com.hibernate.voDao.BillsBio", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(BillsBio instance) {
		log.debug("finding BillsBio instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.BillsBio").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding BillsBio instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from BillsBio as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBioname(Object bioname) {
		return findByProperty(BIONAME, bioname);
	}

	public List findByBsign(Object bsign) {
		return findByProperty(BSIGN, bsign);
	}

	@Override
	public List findAll() {
		log.debug("finding all BillsBio instances");
		try {
			String queryString = "from BillsBio";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public BillsBio merge(BillsBio detachedInstance) {
		log.debug("merging BillsBio instance");
		try {
			BillsBio result = (BillsBio) getSession().merge(detachedInstance);
			getSession().flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(BillsBio instance) {
		log.debug("attaching dirty BillsBio instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(BillsBio instance) {
		log.debug("attaching clean BillsBio instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.hibernate.voDao.DAO#findById(java.lang.Integer)
	 */
	@Override
	public BillsBio findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
}