package com.hibernate.voDao;

import java.util.Collection;
import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A data access object (DAO) providing persistence and search support for Users
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.hibernate.voDao.Users
 * @author MyEclipse Persistence Tools
 */
public class UsersDAO extends BaseHibernateDAO implements DAO<Users> {
	private static final Logger log = LoggerFactory.getLogger(UsersDAO.class);
	// property constants
	public static final String USERNAME = "username";
	public static final String LOGINID = "loginid";
	public static final String PASSWORD = "password";
	public static final String REALNAME = "realname";
	public static final String IDENTITY_CARD_NUMBER = "identityCardNumber";
	public static final String EMAIL = "email";
	public static final String AVATARSTATUS = "avatarstatus";
	public static final String ADMINID = "adminid";

	@Override
	public void save(Users transientInstance) {
		log.debug("saving Users instance");
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
	public void delete(Users persistentInstance) {
		log.debug("deleting Users instance");
		try {
			getSession().delete(persistentInstance);
			getSession().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public Users findById(java.lang.Integer id) {
		log.debug("getting Users instance with id: " + id);
		try {
			Users instance = (Users) getSession().get("com.hibernate.voDao.Users", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List findByIds(Collection ids) {
		log.debug("getting Users instance with id: " + ids);
		try {
			List list = getSession().createCriteria(Users.class).add(Restrictions.in("uid", ids)).list();
			return list;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(Users instance) {
		log.debug("finding Users instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.Users").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Users instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Users as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByLoginid(Object loginid) {
		return findByProperty(LOGINID, loginid);
	}

	public List findByUsername(Object username) {
		return findByProperty(USERNAME, username);
	}

	public List findByPassword(Object password) {
		return findByProperty(PASSWORD, password);
	}

	public List findByRealname(Object realname) {
		return findByProperty(REALNAME, realname);
	}

	public List findByIdentityCardNumber(Object identityCardNumber) {
		return findByProperty(IDENTITY_CARD_NUMBER, identityCardNumber);
	}

	public List findByEmail(Object email) {
		return findByProperty(EMAIL, email);
	}

	public List findByAvatarstatus(Object avatarstatus) {
		return findByProperty(AVATARSTATUS, avatarstatus);
	}

	public List findByAdminid(Object adminid) {
		return findByProperty(ADMINID, adminid);
	}

	@Override
	public List findAll() {
		log.debug("finding all Users instances");
		try {
			String queryString = "from Users";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public Users merge(Users detachedInstance) {
		log.debug("merging Users instance");
		try {
			Users result = (Users) getSession().merge(detachedInstance);
			getSession().flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(Users instance) {
		log.debug("attaching dirty Users instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(Users instance) {
		log.debug("attaching clean Users instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/**
	 * 生成用户
	 * 
	 * @createTime 2014-12-13 上午2:56:09
	 */
	public void generateIdentification(String loginId) {
		log.debug("generateIdentification Users instance");
		try {
			String sql = "update users t set t.identification=UPPER(MD5(t.loginid)) where t.loginid='" + loginId + "' and LENGTH(t.identification)=0";
			getSession().createSQLQuery(sql).executeUpdate();
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/**
	 * 更新jPushId
	 * 
	 * @param jPushId
	 * @param loginId
	 * @createTime 2014-12-15 下午9:50:20
	 */
	public void updateJPushId(String jPushId, String loginId) {
		log.debug("generateIdentification Users instance");
		try {
			String sql = "update users t set t.jpushid='" + jPushId + "' where t.uid='" + loginId + "' ";
			getSession().createSQLQuery(sql).executeUpdate();
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public List findByKeyWords(String keyWords) {
		log.debug("findByKeyWords 查找好友");
		try {
			String sql = "from Users t where t.loginid like ? or t.username like ? or t.realname like ?";
			Query query = getSession().createQuery(sql);
			query.setParameter(0, "%" + keyWords + "%");
			query.setParameter(1, "%" + keyWords + "%");
			query.setParameter(2, "%" + keyWords + "%");
			return query.list();
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}
}