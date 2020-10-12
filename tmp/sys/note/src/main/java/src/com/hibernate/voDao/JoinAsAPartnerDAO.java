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
 * JoinAsAPartner entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hibernate.voDao.JoinAsAPartner
 * @author MyEclipse Persistence Tools
 */
public class JoinAsAPartnerDAO extends BaseHibernateDAO implements DAO<JoinAsAPartner> {
	private static final Logger log = LoggerFactory.getLogger(JoinAsAPartnerDAO.class);
	// property constants
	public static final String UID = "uid";
	public static final String USERNAME = "username";
	public static final String JAAPISSPONSOR = "jaapissponsor";
	public static final String JAAPTITLE = "jaaptitle";
	public static final String JAAPCONTENT = "jaapcontent";
	public static final String JAAPCOST = "jaapcost";
	public static final String JAAPADVICE = "jaapadvice";
	public static final String JAAPTHENUMBEROFPERSON = "jaapthenumberofperson";
	public static final String JAAPPERMISSION = "jaappermission";
	public static final String JAAPJOINSTATE = "jaapjoinstate";
	public static final String JAAPAPPLYID = "jaapapplyid";
	public static final String JAAPISFINISH = "jaapisfinish";
	public static final String BID = "bid";
	public static final String JAAPEVALUATE = "jaapevaluate";
	public static final String JAAPSUMMARY = "jaapsummary";

	@Override
	public void save(JoinAsAPartner transientInstance) {
		JoinAsAPartnerDAO.log.debug("saving JoinAsAPartner instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			getSession().flush();
			JoinAsAPartnerDAO.log.debug("save successful");
		} catch (RuntimeException re) {
			JoinAsAPartnerDAO.log.error("save failed", re);
			throw re;
		}
	}

	@Override
	public void delete(JoinAsAPartner persistentInstance) {
		JoinAsAPartnerDAO.log.debug("deleting JoinAsAPartner instance");
		try {
			String sql = "delete from join_as_a_partner where jaapapplyid='" + persistentInstance.getJaapid() + "'";
			Query queryObject = getSession().createSQLQuery(sql);
			queryObject.executeUpdate();

			persistentInstance = (JoinAsAPartner) getSession().load(JoinAsAPartner.class, persistentInstance.getJaapid());
			getSession().delete(persistentInstance);
			getSession().flush();
			JoinAsAPartnerDAO.log.debug("delete successful");
		} catch (RuntimeException re) {
			JoinAsAPartnerDAO.log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public JoinAsAPartner findById(java.lang.Integer id) {
		JoinAsAPartnerDAO.log.debug("getting JoinAsAPartner instance with id: " + id);
		try {
			JoinAsAPartner instance = (JoinAsAPartner) getSession().get("com.hibernate.voDao.JoinAsAPartner", id);
			return instance;
		} catch (RuntimeException re) {
			JoinAsAPartnerDAO.log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(JoinAsAPartner instance) {
		JoinAsAPartnerDAO.log.debug("finding JoinAsAPartner instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.JoinAsAPartner").add(Example.create(instance)).list();
			JoinAsAPartnerDAO.log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			JoinAsAPartnerDAO.log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		JoinAsAPartnerDAO.log.debug("finding JoinAsAPartner instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from JoinAsAPartner as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			JoinAsAPartnerDAO.log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUid(Object uid) {
		return findByProperty(JoinAsAPartnerDAO.UID, uid);
	}

	public List findByUsername(Object username) {
		return findByProperty(JoinAsAPartnerDAO.USERNAME, username);
	}

	public List findByJaapissponsor(Object jaapissponsor) {
		return findByProperty(JoinAsAPartnerDAO.JAAPISSPONSOR, jaapissponsor);
	}

	public List findByJaaptitle(Object jaaptitle) {
		return findByProperty(JoinAsAPartnerDAO.JAAPTITLE, jaaptitle);
	}

	public List findByJaapcontent(Object jaapcontent) {
		return findByProperty(JoinAsAPartnerDAO.JAAPCONTENT, jaapcontent);
	}

	public List findByJaapcost(Object jaapcost) {
		return findByProperty(JoinAsAPartnerDAO.JAAPCOST, jaapcost);
	}

	public List findByJaapadvice(Object jaapadvice) {
		return findByProperty(JoinAsAPartnerDAO.JAAPADVICE, jaapadvice);
	}

	public List findByJaapthenumberofpeople(Object jaapthenumberofperson) {
		return findByProperty(JoinAsAPartnerDAO.JAAPTHENUMBEROFPERSON, jaapthenumberofperson);
	}

	public List findByJaappermission(Object jaappermission) {
		return findByProperty(JoinAsAPartnerDAO.JAAPPERMISSION, jaappermission);
	}

	public List findByJaapisfinish(Object jaapisfinish) {
		return findByProperty(JoinAsAPartnerDAO.JAAPISFINISH, jaapisfinish);
	}

	public List findByBid(Object bid) {
		return findByProperty(JoinAsAPartnerDAO.BID, bid);
	}

	public List findByJaapevaluate(Object jaapevaluate) {
		return findByProperty(JoinAsAPartnerDAO.JAAPEVALUATE, jaapevaluate);
	}

	public List findByJaapsummary(Object jaapsummary) {
		return findByProperty(JoinAsAPartnerDAO.JAAPSUMMARY, jaapsummary);
	}

	@Override
	public List findAll() {
		JoinAsAPartnerDAO.log.debug("finding all JoinAsAPartner instances");
		try {
			String queryString = "from JoinAsAPartner";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			JoinAsAPartnerDAO.log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public JoinAsAPartner merge(JoinAsAPartner detachedInstance) {
		JoinAsAPartnerDAO.log.debug("merging JoinAsAPartner instance");
		try {
			JoinAsAPartner result = (JoinAsAPartner) getSession().merge(detachedInstance);
			getSession().flush();
			JoinAsAPartnerDAO.log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			JoinAsAPartnerDAO.log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(JoinAsAPartner instance) {
		JoinAsAPartnerDAO.log.debug("attaching dirty JoinAsAPartner instance");
		try {
			getSession().saveOrUpdate(instance);
			JoinAsAPartnerDAO.log.debug("attach successful");
		} catch (RuntimeException re) {
			JoinAsAPartnerDAO.log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(JoinAsAPartner instance) {
		JoinAsAPartnerDAO.log.debug("attaching clean JoinAsAPartner instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			JoinAsAPartnerDAO.log.debug("attach successful");
		} catch (RuntimeException re) {
			JoinAsAPartnerDAO.log.error("attach failed", re);
			throw re;
		}
	}

	/**
	 * 查找用户在搭伙中的状态，用于给用户显示按钮，避免多次加入
	 * 
	 * @return
	 * @createTime 2014-5-3 下午3:00:19
	 */
	public List findByUidAndJaapid(String uid, String jaapid) {
		JoinAsAPartnerDAO.log.debug("finding record from JoinAsAPartner instances where uid=? and jaapid=? limit 1");
		try {
			String queryString = "from JoinAsAPartner a where a.uid='" + uid + "' and a.jaapapplyid='" + jaapid + "' order by a.jaapjoinstate desc ";
			Query queryObject = getSession().createQuery(queryString);
			// queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			queryObject.setMaxResults(1);
			return queryObject.list();
		} catch (RuntimeException re) {
			JoinAsAPartnerDAO.log.error("find all failed", re);
			throw re;
		}
	}

	/**
	 * 用于显示所有的搭伙
	 * 
	 * @return
	 * @createTime 2014-5-3 下午4:12:17
	 */
	public List findAllPartner(int pageNumber, int numPerPage) {
		JoinAsAPartnerDAO.log.debug("finding all JoinAsAPartner instances");
		try {
			String queryString = "from JoinAsAPartner a where a.jaapissponsor=true order by a.jaappublicdate desc";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setFirstResult(pageNumber * numPerPage);
			queryObject.setMaxResults(numPerPage);
			return queryObject.list();
		} catch (RuntimeException re) {
			JoinAsAPartnerDAO.log.error("find all failed", re);
			throw re;
		}
	}

	/**
	 * 用来为findAllPartner函数计算总数
	 * 
	 * @return
	 * @createTime 2014-5-3 下午5:03:30
	 */
	public String findAllPartnerCount() {
		JoinAsAPartnerDAO.log.debug("finding all JoinAsAPartner instances");
		try {
			String queryString = "select count(1) as counts from join_as_a_partner a where a.jaapissponsor=true";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List list = queryObject.list();
			Map map = (Map) list.get(0);
			return map.get("counts").toString();
		} catch (RuntimeException re) {
			JoinAsAPartnerDAO.log.error("find all failed", re);
			throw re;
		}
	}

	/**
	 * 根据搭伙的id查找所有申请人
	 * 
	 * @return
	 * @createTime 2014-5-3 下午5:43:55
	 */
	public List findAllApply(String id) {
		log.debug("finding all JoinAsAPartner instances");
		try {
			String queryString = "select a.username,a.jaapadvice,b.telephone from join_as_a_partner a left join users b on a.uid=b.uid where a.jaapapplyid='" + id
					+ "' order by a.jaappublicdate desc";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return queryObject.list();
		} catch (RuntimeException re) {
			JoinAsAPartnerDAO.log.error("find all failed", re);
			throw re;
		}
	}

	/**
	 * 通过属性查找通过 计算出的页面的条目，显示中文 Android
	 * 
	 * @param propertyName
	 *            属性名(此处使用uid)
	 * @param value
	 *            属性值
	 * @param pageNumber
	 *            页码
	 * @param numPerPage
	 *            每页条数
	 * @param startDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 */
	public List findByPropertyPaging(String parameter1, String parameter2, String parameter3, int pageNumber, int numPerPage, String startDate, String endDate) {
		JoinAsAPartnerDAO.log.debug("finding join_as_a_partner  limit pageNumber * numPerPage");
		try {
			String subQueryString = "";
			/*
			 * parameter形式为 p1,v1;p2,v2;p3,v3 parameter1的值为= parameter2的值为like
			 * parameter3直接写sql语句
			 */
			String[] p = parameter1.split(";");
			if (!p[0].isEmpty()) {
				for (int i = 0; i < p.length; i++) {
					String[] pv = p[i].split(",");
					subQueryString += " x." + pv[0] + "='" + pv[1] + "' and ";
				}
			}
			p = parameter2.split(";");
			if (!p[0].isEmpty()) {
				for (int i = 0; i < p.length; i++) {
					String[] pv = p[i].split(",");
					subQueryString += " x." + pv[0] + " like '%" + pv[1] + "%' and ";
				}
			}
			subQueryString += parameter3;
			String queryString = "select a.uid,a.username,a.telephone,t.jaapid,t.jaaptitle,t.jaapdeadline,'' as jaapcontent, ( SELECT count(1) FROM join_as_a_partner x WHERE x.jaapapplyid = t.jaapid ) as joinnum from join_as_a_partner t inner join users a on a.uid=t.uid where "
					+ subQueryString + "t.jaapjoinstate = 0 order by t.jaapid desc";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

			queryObject.setFirstResult(pageNumber * numPerPage);
			queryObject.setMaxResults(numPerPage);
			return queryObject.list();
		} catch (RuntimeException re) {
			JoinAsAPartnerDAO.log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * 查找所有已经申请此jaapid的人
	 * 
	 * @param propertyName
	 * @param value
	 * @return
	 * @createTime 2014-9-13 下午1:14:43
	 */
	public List findByUidJaapid(Object value) {
		JoinAsAPartnerDAO.log.debug("finding JoinAsAPartner instance with property: value: " + value);
		try {
			String queryString = "select t.jaapadvice,a.uid,a.username,a.telephone from join_as_a_partner as t inner join users a on a.uid=t.uid where t.jaapapplyid= ?";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			JoinAsAPartnerDAO.log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * 检查用户是否已经加入这个搭伙
	 * 
	 * @param jaapid
	 * @param uid
	 * @return
	 * @createTime 2014-9-13 下午1:29:40
	 */
	public List checkUidJaapid(Object value1, Object value2) {
		JoinAsAPartnerDAO.log.debug("finding JoinAsAPartner instance with property: value: " + value1);
		try {
			String queryString = "select 1 from join_as_a_partner as t inner join users a on a.uid=t.uid where t.jaapapplyid= ? and a.uid=?";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			queryObject.setParameter(0, value1);
			queryObject.setParameter(1, value2);
			return queryObject.list();
		} catch (RuntimeException re) {
			JoinAsAPartnerDAO.log.error("find by property name failed", re);
			throw re;
		}
	}
}