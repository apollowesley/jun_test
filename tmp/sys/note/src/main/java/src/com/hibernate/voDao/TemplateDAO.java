package com.hibernate.voDao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for
 * Template entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see com.hibernate.voDao.Template
 * @author MyEclipse Persistence Tools
 */
public class TemplateDAO extends BaseHibernateDAO implements DAO<Template> {
	private static final Logger log = LoggerFactory.getLogger(TemplateDAO.class);
	// property constants
	public static final String TNAME = "tname";
	public static final String TDIRECTORY = "tdirectory";
	public static final String TCOPYRIGHT = "tcopyright";
	public static final String TTYPE = "ttype";

	@Override
	public void save(Template transientInstance) {
		log.debug("saving Template instance");
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
	public void delete(Template persistentInstance) {
		log.debug("deleting Template instance");
		try {
			persistentInstance = (Template) getSession().load(Template.class, persistentInstance.getTid());
			getSession().delete(persistentInstance);
			getSession().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public Template findById(Integer id) {
		log.debug("getting Template instance with id: " + id);
		try {
			Template instance = (Template) getSession().get("com.hibernate.voDao.Template", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(Template instance) {
		log.debug("finding Template instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.Template").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Template instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Template as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByTname(Object tname) {
		return findByProperty(TNAME, tname);
	}

	public List findByTdirectory(Object tdirectory) {
		return findByProperty(TDIRECTORY, tdirectory);
	}

	public List findByTcopyright(Object tcopyright) {
		return findByProperty(TCOPYRIGHT, tcopyright);
	}

	public List findByTtype(Object ttype) {
		return findByProperty(TTYPE, ttype);
	}

	@Override
	public List findAll() {
		log.debug("finding all Template instances");
		try {
			String queryString = "from Template";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public Template merge(Template detachedInstance) {
		log.debug("merging Template instance");
		try {
			Template result = (Template) getSession().merge(detachedInstance);
			getSession().flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(Template instance) {
		log.debug("attaching dirty Template instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(Template instance) {
		log.debug("attaching clean Template instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

}