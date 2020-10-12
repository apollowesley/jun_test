package com.hibernate.voDao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * BillsImages entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hibernate.voDao.BillsImages
 * @author MyEclipse Persistence Tools
 */
public class BillsImagesDAO extends BaseHibernateDAO implements DAO<BillsImages> {
	private static final Logger log = LoggerFactory.getLogger(BillsImagesDAO.class);
	// property constants
	public static final String BIBILLID = "bibillid";
	public static final String BIDIR = "bidir";
	public static final String BIORIGINALNAME = "bioriginalname";
	public static final String UID = "uid";
	public static final String USERNAME = "username";

	@Override
	public void save(BillsImages transientInstance) {
		log.debug("saving BillsImages instance");
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
	public void delete(BillsImages persistentInstance) {
		log.debug("deleting BillsImages instance");
		try {
			persistentInstance = (BillsImages) getSession().load(BillsImages.class, persistentInstance.getBiid());
			getSession().delete(persistentInstance);
			getSession().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public BillsImages findById(java.lang.Integer id) {
		log.debug("getting BillsImages instance with id: " + id);
		try {
			BillsImages instance = (BillsImages) getSession().get("com.hibernate.voDao.BillsImages", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(BillsImages instance) {
		log.debug("finding BillsImages instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.BillsImages").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding BillsImages instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from BillsImages as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByBibillid(Object bibillid) {
		return findByProperty(BIBILLID, bibillid);
	}

	public List findByBidir(Object bidir) {
		return findByProperty(BIDIR, bidir);
	}

	public List findByBioriginalname(Object bioriginalname) {
		return findByProperty(BIORIGINALNAME, bioriginalname);
	}

	public List findByUid(Object uid) {
		return findByProperty(UID, uid);
	}

	public List findByUsername(Object username) {
		return findByProperty(USERNAME, username);
	}

	@Override
	public List findAll() {
		log.debug("finding all BillsImages instances");
		try {
			String queryString = "from BillsImages";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public BillsImages merge(BillsImages detachedInstance) {
		log.debug("merging BillsImages instance");
		try {
			BillsImages result = (BillsImages) getSession().merge(detachedInstance);
			getSession().flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(BillsImages instance) {
		log.debug("attaching dirty BillsImages instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(BillsImages instance) {
		log.debug("attaching clean BillsImages instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}