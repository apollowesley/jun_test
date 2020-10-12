package com.hibernate.voDao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.beans.StatisticsSQLResult;
import com.staticClass.Const;

/**
 * A data access object (DAO) providing persistence and search support for Bills
 * entities. Transaction control of the save(), update() and delete() operations
 * can directly support Spring container-managed transactions or they can be
 * augmented to handle user-managed Spring transactions. Each of these methods
 * provides additional information for how to configure it for the desired type
 * of transaction control.
 * 
 * @see com.hibernate.voDao.Bills
 * @author MyEclipse Persistence Tools
 */
public class BillsDAO extends BaseHibernateDAO implements DAO<Bills> {
	private static final Logger log = LoggerFactory.getLogger(BillsDAO.class);
	// property constants
	public static final String USERNAME = "username";
	public static final String UID = "uid";
	public static final String BDate = "bdate";
	public static final String BTYPE = "btype";
	public static final String BIO = "bio";
	public static final String BAMOUNT = "bamount";
	public static final String BCAPTION = "bcaption";
	public static final String BBETRAVELLEADER = "bbetravelleader";
	public static final String BBETRAVELMEMBER = "bbetravelmember";
	public static final String BTID = "btid";
	public static final String BIMAGEID = "bimageid";
	public static final String BCTYPE = "bctype";
	public static final String REPLYS = "replys";
	public static final String FORWARDS = "forwards";
	public static final String ROOTBID = "rootbid";
	public static final String FROMBID = "frombid";
	public static final String FROMUID = "fromuid";
	public static final String FROMUNAME = "fromuname";
	public static final String FROM = "from";
	public static final String FROMIP = "fromip";
	public static final String ISDELETED = "isdeleted";

	@Override
	public void save(Bills transientInstance) {
		log.debug("saving Bills instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			getSession().flush();
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	/** 逻辑删除，从数据库中标记删除，用户使用 */
	@Override
	public void delete(Bills persistentInstance) {
		log.debug("deleting Bills instance");
		try {
			persistentInstance.setIsdeleted(true);
			merge(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	/** 物理删除，从数据库中彻底删除，管理员使用 */
	public void deletePhysics(Bills persistentInstance) {
		log.debug("deleting Bills instance");
		try {
			persistentInstance = (Bills) getSession().load(Bills.class, persistentInstance.getBid());
			getSession().delete(persistentInstance);
			getSession().flush();
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	@Override
	public Bills findById(java.lang.Integer id) {
		log.debug("getting Bills instance with id: " + id);
		try {
			Bills instance = (Bills) getSession().get("com.hibernate.voDao.Bills", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(Bills instance) {
		log.debug("finding Bills instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.Bills").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding Bills instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from Bills as model where model." + propertyName + "= ?" + " ORDER BY " + BDate + " desc";
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

	public List findByBtype(Object btype) {
		return findByProperty(BTYPE, btype);
	}

	public List findByBio(Object bio) {
		return findByProperty(BIO, bio);
	}

	public List findByBamount(Object bamount) {
		return findByProperty(BAMOUNT, bamount);
	}

	public List findByBcaption(Object bcaption) {
		return findByProperty(BCAPTION, bcaption);
	}

	public List findByBbetravelleader(Object bbetravelleader) {
		return findByProperty(BBETRAVELLEADER, bbetravelleader);
	}

	public List findByBbetravelmember(Object bbetravelmember) {
		return findByProperty(BBETRAVELMEMBER, bbetravelmember);
	}

	public List findByBtid(Object btid) {
		return findByProperty(BTID, btid);
	}

	public List findByBimageid(Object bimageid) {
		return findByProperty(BIMAGEID, bimageid);
	}

	public List findByBctype(Object bctype) {
		return findByProperty(BCTYPE, bctype);
	}

	public List findByReplys(Object replys) {
		return findByProperty(REPLYS, replys);
	}

	public List findByForwards(Object forwards) {
		return findByProperty(FORWARDS, forwards);
	}

	public List findByRootbid(Object rootbid) {
		return findByProperty(ROOTBID, rootbid);
	}

	public List findByFrombid(Object frombid) {
		return findByProperty(FROMBID, frombid);
	}

	public List findByFromuid(Object fromuid) {
		return findByProperty(FROMUID, fromuid);
	}

	public List findByFromuname(Object fromuname) {
		return findByProperty(FROMUNAME, fromuname);
	}

	public List findByFrom(Object from) {
		return findByProperty(FROM, from);
	}

	public List findByFromip(Object fromip) {
		return findByProperty(FROMIP, fromip);
	}

	@Override
	public List findAll() {
		log.debug("finding all Bills instances");
		try {
			String queryString = "from Bills";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public Bills merge(Bills detachedInstance) {
		log.debug("merging Bills instance");
		try {
			Bills result = (Bills) getSession().merge(detachedInstance);
			getSession().flush();
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(Bills instance) {
		log.debug("attaching dirty Bills instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(Bills instance) {
		log.debug("attaching clean Bills instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	/** 主页flotr2图表统计功能使用，主要是按日期查询并求和 */
	public List findByDateStatistics(String startDate, String endDate, String bio, String uid) {
		log.debug("finding all Bills instances");
		try {
			String queryString = "SELECT DATE(bills.bdate) as day,SUM(bills.bamount) as amount FROM bills " + "WHERE bills.bdate BETWEEN '" + startDate + "' AND '" + endDate
					+ "' AND bills.uid = " + uid + " AND bills.bio = " + bio + " GROUP BY CAST(bills.bdate AS DATE) " + "ORDER BY DATE(bills.bdate) ASC ";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.aliasToBean(StatisticsSQLResult.class));
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	/**
	 * 通过属性查找通过 计算出的页面的条目，同时限制时间，通过与表bio联合查询，显示中文
	 * 
	 * @param parameter1
	 *            等号连接sql 参数形式如：key,value;key1,value1
	 * @param parameter2
	 *            like连接sql 参数形式如：key,value;key1,value1
	 * @param parameter3
	 *            自定义sql
	 * @param pageNumber
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @return
	 * @createTime 2014-11-9 下午10:40:43
	 */
	public List findByPropertyPaging(String parameter1, String parameter2, String parameter3, int pageNumber, int numPerPage, String startDate, String endDate) {
		return findByPropertyPaging(parameter1, parameter2, parameter3, pageNumber, numPerPage, startDate, endDate, null);
	}

	/**
	 * 通过属性查找通过 计算出的页面的条目，同时限制时间，通过与表bio联合查询，显示中文
	 * 
	 * @param parameter1
	 *            等号连接sql 参数形式如：key,value;key1,value1
	 * @param parameter2
	 *            like连接sql 参数形式如：key,value;key1,value1
	 * @param parameter3
	 *            自定义sql
	 * @param pageNumber
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param myUid
	 *            登录用户的uid
	 * @return
	 * @createTime 2014-11-9 下午10:40:43
	 */
	public List findByPropertyPaging(String parameter1, String parameter2, String parameter3, int pageNumber, int numPerPage, String startDate, String endDate, String myUid) {
		log.debug("finding Bills,BillsBio instance with property: propertyName, value:value  limit pageNumber * numPerPage");
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
			/** IFNULL(d.bidir,'null') 兼容旧版本 **/
			String queryString = "SELECT a.*,b.bioname,b.bsign,c.btypename,a.bimageid,'' bidirs,IFNULL(d.bidir,'null') as bidir,e.id as likeid FROM "
					+ "(((select x.bid,x.bio,x.bamount,x.btype,x.bimageid,x.bdate,x.bcaption,x.bbetravelleader,x.btid,x.replys,x.forwards,x.username,x.uid,x.bctype,x.rootbid,x.like_count from bills x where "
					+ subQueryString + " (x.bctype=0 or x.bctype=2) and x.bdate between '" + startDate + "' AND '" + endDate
					+ "' and isdeleted=0) a left join bills_bio b on a.bio=b.bio) left join bills_type c on c.btypeid=a.btype)" + " left join  bills_images d ON d.biid=a.bimageid "
					+ "left JOIN bills_like e on e.uid='" + myUid + "' and e.bid=a.bid " + " ORDER BY a.bdate " + (numPerPage > 100000 ? "asc" : "desc");
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			queryObject.setFirstResult(pageNumber * numPerPage);
			queryObject.setMaxResults(numPerPage);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/** 此函数是为findByPropertyPaging()函数计算总数量，用来显示分页符号，参数与其相同 */
	public String findByPropertyPagingCounts(String parameter1, String parameter2, String parameter3, int pageNumber, int numPerPage, String startDate, String endDate) {
		log.debug("finding Bills,BillsBio instance with property: propertyName, value:value  limit pageNumber * numPerPage");
		try {
			String subQueryString = "";
			/* parameter形式为 p1,v1;p2,v2;p3,v3 parameter1的值为= parameter2的值为like */
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
			String queryString = "select count(1) as counts from bills x where " + subQueryString + " (x.bctype=0 or x.bctype=2) and x.bdate between '" + startDate + "' AND '"
					+ endDate + "' and isdeleted=0 limit 1";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List list = queryObject.list();
			Map map = (Map) list.get(0);
			return map.get("counts").toString();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * 通过属性查找通过 计算出的页面的条目，同时限制时间，通过与表bio联合查询，显示中文
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
	public List findByPropertyPaging(String parameter1, String parameter2, int pageNumber, int numPerPage, String startDate, String endDate) {
		log.debug("finding Bills,BillsBio instance with property: propertyName, value:value  limit pageNumber * numPerPage");
		try {
			String subQueryString = "";
			/* parameter形式为 p1,v1;p2,v2;p3,v3 parameter1的值为= parameter2的值为like */
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
			String queryString = "SELECT a.*,b.bioname,b.bsign,c.btypename,d.bidir FROM "
					+ "(((select x.bid,x.bio,x.bamount,x.btype,x.bimageid,x.bdate,x.bcaption,x.bbetravelleader,x.btid,x.replys,x.forwards,x.username,x.uid,x.bctype,x.rootbid from bills x where "
					+ subQueryString + " (x.bctype=0 or x.bctype=2) and x.bdate between '" + startDate + "' AND '" + endDate
					+ "' and isdeleted=0) a left join bills_bio b on a.bio=b.bio) left join bills_type c on c.btypeid=a.btype)"
					+ " left join  bills_images d ON d.biid=a.bimageid ORDER BY a.bdate desc";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			queryObject.setFirstResult(pageNumber * numPerPage);
			queryObject.setMaxResults(numPerPage);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/** 此函数是为findByPropertyPaging()函数计算总数量，用来显示分页符号，参数与其相同 */
	public String findByPropertyPagingCounts(String parameter1, String parameter2, int pageNumber, int numPerPage, String startDate, String endDate) {
		log.debug("finding Bills,BillsBio instance with property: propertyName, value:value  limit pageNumber * numPerPage");
		try {
			String subQueryString = "";
			/* parameter形式为 p1,v1;p2,v2;p3,v3 parameter1的值为= parameter2的值为like */
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
			String queryString = "select count(1) as counts from bills x where " + subQueryString + " (x.bctype=0 or x.bctype=2) and x.bdate between '" + startDate + "' AND '"
					+ endDate + "' and isdeleted=0 limit 1";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List list = queryObject.list();
			Map map = (Map) list.get(0);
			return map.get("counts").toString();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/** 用来获取最近的5条行程记录 */
	public List findByPropertyTravelLeader(String propertyName, Object value) {
		log.debug("finding all Bills instances");
		try {
			String queryString = "select a.bid,c.btypename from bills a,bills_type c where  a." + propertyName + "= ? and a." + BBETRAVELLEADER + "= true"
					+ " and c.btypeid=a.btype ORDER BY " + BDate + " desc";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			queryObject.setParameter(0, value);
			queryObject.setFirstResult(0);
			queryObject.setMaxResults(5);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/** 用来获取行程子记录 */
	public List findByPropertyTravelMember(String Btid) {
		log.debug("finding all Bills instances");
		try {
			String queryString = "select a.*,b.bioname,b.bsign,c.btypename,d.bidir from (((select x.bid,x.bio,x.bamount,x.btype,x.bimageid,x.bdate,x.bcaption,x.replys,x.forwards,x.username,x.uid from bills x where x."
					+ BTID
					+ "="
					+ Btid
					+ " and x."
					+ BBETRAVELMEMBER
					+ "=true and isdeleted=0) a left join bills_bio b on a.bio=b.bio) left join bills_type c on c.btypeid=a.btype)"
					+ " left join  bills_images d ON d.biid=a.bimageid ORDER BY a.bdate desc";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/** 获取当前bid记录的回复 */
	public int findReplyCountBybid(String bid) {
		log.debug("finding Bills_type instance with uid");
		try {
			String queryString = "select count(*) from bills x where " + " x.bctype=1 and x.rootbid = (SELECT DISTINCT rootbid FROM bills WHERE frombid = '" + bid
					+ "')  and isdeleted=0";
			Query queryObject = getSession().createSQLQuery(queryString);
			return Integer.parseInt(queryObject.list().get(0).toString());
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/** 获取当前bid记录的回复 */
	public List findReplyBybid(String bid) {
		return findReplyBybid(bid, null);
	}

	/** 获取当前bid记录的回复 发布时间小于date的 */
	public List findReplyBybid(String bid, String date) {
		log.debug("finding Bills_type instance with uid");
		try {
			String queryString = "select x.bid,x.bio,x.bamount,x.btype,x.bimageid,x.bdate,x.bcaption,x.bbetravelleader,x.btid,x.replys,x.forwards,x.username,x.uid from bills x where "
					+ " x.bctype=1 and x.rootbid = (SELECT DISTINCT rootbid FROM bills WHERE frombid = '"
					+ bid
					+ "')  and isdeleted=0  "
					+ (StringUtils.isNotBlank(date) ? " and bdate <'" + date + "'" : "") + " order by x.bdate asc limit 1000";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/** 获取当前bid记录的转发 */
	public List findForwardBybid(String bid) {
		log.debug("finding Bills_type instance with uid");
		try {
			String queryString = "select x.bid,x.bio,x.bamount,x.btype,x.bimageid,x.bdate,x.bcaption,x.bbetravelleader,x.btid,x.replys,x.forwards,x.username,x.uid from bills x where "
					+ " x.bctype=2 and x.frombid='" + bid + "' and isdeleted=0 order by x.bdate desc limit 12";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/** 获取当前bid记录 */
	public List findBybid(String bid) {
		log.debug("finding Bills,BillsBio instance with property: propertyName, value:value  limit pageNumber * numPerPage");
		try {
			String queryString = "SELECT a.*,b.bioname,b.bsign,c.btypename,d.bidir FROM "
					+ "(((select x.bid,x.bio,x.bamount,x.btype,x.bimageid,x.bdate,x.bcaption,x.bbetravelleader,x.btid,x.replys,x.forwards,x.username,x.uid,x.bctype,x.rootbid from bills x where "
					+ "x.bid='" + bid + "' and (x.bctype=0 or x.bctype=2) " + " and isdeleted=0) a left join bills_bio b on a.bio=b.bio) left join bills_type c on c.btypeid=a.btype)"
					+ " left join  bills_images d ON d.biid=a.bimageid ORDER BY a.bdate desc limit 1";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * 此方法用于查询出给定 话题名称 的分页内容
	 * 
	 * @param topicName
	 * @return
	 * @createTime 2014-5-1 上午12:24:46
	 */
	public List findAllByTopicName(String topicName, int pageNumber, int numPerPage) {
		log.debug("此方法用于查询出给定 话题名称 的分页内容");
		try {
			String queryString = "SELECT a.*,b.bioname,b.bsign,c.btypename,d.bidir FROM "
					+ "(((select x.bid,x.bio,x.bamount,x.btype,x.bimageid,x.bdate,x.bcaption,x.bbetravelleader,x.btid,x.replys,x.forwards,x.username,x.uid,x.bctype,x.rootbid from bills x where "
					+ " x.bcaption like '%" + topicName + "%' and (x.bctype=0 or x.bctype=2) "
					+ " and isdeleted=0) a left join bills_bio b on a.bio=b.bio) left join bills_type c on c.btypeid=a.btype)"
					+ " left join  bills_images d ON d.biid=a.bimageid ORDER BY a.bdate desc";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			queryObject.setFirstResult(pageNumber * numPerPage);
			queryObject.setMaxResults(numPerPage);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * 此方法用于查询出给定 话题名称 的分页条目数
	 * 
	 * @param topicName
	 * @return
	 * @createTime 2014-5-1 上午12:25:28
	 */
	public String findAllByTopicNameCount(String topicName) {
		log.debug("此方法用于查询出给定 话题名称 的分页条目数");
		try {
			String queryString = "select count(1) as counts from bills x where " + " x.bcaption like '%" + topicName + "%' and (x.bctype=0 or x.bctype=2) and isdeleted=0 limit 1 ";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List list = queryObject.list();
			Map map = (Map) list.get(0);
			return map.get("counts").toString();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * 此方法用于查询当前用户所有的记录的id
	 * 
	 * @param topicName
	 * @return
	 * @createTime 2014-5-1 上午12:25:28
	 */
	public List findAllId(String uid) {
		log.debug("此方法用于查询出给定 话题名称 的分页条目数");
		try {
			String queryString = "select bid from bills x where " + " x.uid = '" + uid + "' and (x.bctype=0 or x.bctype=2) and isdeleted=0";
			Query queryObject = getSession().createSQLQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public Object findById(String id) {
		log.debug("getting bills instance with id: " + id);
		try {
			String queryString = "SELECT a.*,b.bioname,b.bsign,c.btypename,'' bidirs,IFNULL(d.bidir,'null') as bidir FROM "
					+ "(((select x.bid,x.bio,x.bamount,x.btype,x.bimageid,x.bdate,x.bcaption,x.bbetravelleader,x.btid,x.replys,x.forwards,x.username,x.uid,x.bctype,x.rootbid from bills x where  x.bid='"
					+ id + "' and (x.bctype=0 or x.bctype=2) and isdeleted=0) a left join bills_bio b on a.bio=b.bio) left join bills_type c on c.btypeid=a.btype)"
					+ " left join  bills_images d ON d.biid=a.bimageid limit 1";
			Query queryObject = getSession().createSQLQuery(queryString);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return queryObject.list().get(0);
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	/**
	 * 获取最新回复的几条
	 * 
	 * @param pageNumber
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param myUid
	 * @param bids
	 * @param num
	 * @param date
	 * @param direction
	 * @return
	 * @createTime 2015-5-9 下午10:10:25
	 */
	public List findByPropertyPaging(int pageNumber, int numPerPage, String startDate, String endDate, String myUid, String bids, String num, String date, boolean direction) {
		log.debug("finding Bills,BillsBio,replys instance limit pageNumber * numPerPage");
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT                                                ");
			sb.append(" 	 a.*, b.bioname,                                  ");
			sb.append(" 	b.bsign,                                          ");
			sb.append(" 	c.btypename,                                      ");
			sb.append(" 	a.bimageid,                                       ");
			sb.append(" 	'' bidirs,                                        ");
			sb.append(" 	IFNULL(d.bidir, 'null') AS bidir,                 ");
			sb.append(" 	e.id AS likeid,                                   ");
			sb.append(" 	max(bs.bdate) as replyDate                        ");
			sb.append(" FROM                                                  ");
			sb.append(" 	(                                                 ");
			sb.append(" 		(                                             ");
			sb.append(" 			(                                         ");
			sb.append(" 				SELECT                                ");
			sb.append(" 					x.bid,                            ");
			sb.append(" 					x.bio,                            ");
			sb.append(" 					x.bamount,                        ");
			sb.append(" 					x.btype,                          ");
			sb.append(" 					x.bimageid,                       ");
			sb.append(" 					x.bdate,                          ");
			sb.append(" 					x.bcaption,                       ");
			sb.append(" 					x.bbetravelleader,                ");
			sb.append(" 					x.btid,                           ");
			sb.append(" 					x.replys,                         ");
			sb.append(" 					x.forwards,                       ");
			sb.append(" 					x.username,                       ");
			sb.append(" 					x.uid,                            ");
			sb.append(" 					x.bctype,                         ");
			sb.append(" 					x.rootbid,                        ");
			sb.append(" 					x.like_count                      ");
			sb.append(" 				FROM                                  ");
			sb.append(" 					bills x                           ");
			sb.append(" 				WHERE                                 ");
			sb.append(" 					(                                 ");
			sb.append(" 						uid = '" + myUid + "'                     ");
			sb.append(" 						OR uid IN (                   ");
			sb.append(" 							SELECT                    ");
			sb.append(" 								user_id_1             ");
			sb.append(" 							FROM                      ");
			sb.append(" 								relationship_friends  ");
			sb.append(" 							WHERE                     ");
			sb.append(" 								user_id_2 = '" + myUid + "'       ");
			sb.append(" 							AND STATUS = '2'          ");
			sb.append(" 						)                             ");
			sb.append(" 						OR uid IN (                   ");
			sb.append(" 							SELECT                    ");
			sb.append(" 								user_id_2             ");
			sb.append(" 							FROM                      ");
			sb.append(" 								relationship_friends  ");
			sb.append(" 							WHERE                     ");
			sb.append(" 								user_id_1 = '" + myUid + "'       ");
			sb.append(" 							AND STATUS = '2'          ");
			sb.append(" 						)                             ");
			sb.append(" 					)                                 ");

			sb.append(" 				AND (x.bctype = 0 OR x.bctype = 2)    ");
			sb.append(" 				AND x.bdate BETWEEN '" + startDate + "'               ");
			sb.append(" 				AND '" + endDate + "'             ");
			sb.append(" 				AND isdeleted = 0                     ");
			sb.append(" 				and replys>0                          ");
			sb.append(" 			) a                                       ");
			sb.append(" 			LEFT JOIN bills_bio b ON a.bio = b.bio    ");
			sb.append(" 		)                                             ");
			sb.append(" 		LEFT JOIN bills_type c ON c.btypeid = a.btype ");
			sb.append(" 	)                                                 ");
			sb.append(" LEFT JOIN bills_images d ON d.biid = a.bimageid       ");
			sb.append(" LEFT JOIN bills_like e ON e.uid = '" + myUid + "'                 ");
			sb.append(" AND e.bid = a.bid                                     ");
			sb.append(" inner join bills bs on bs.rootbid=a.bid               ");
			if (!Const.stringIsEmpty(date)) {
				if (direction) {
					sb.append(" and bs.bdate >'" + date + "' ");
				} else {
					sb.append(" and bs.bdate <'" + date + "' ");
				}

			}
			sb.append("  where a.bid not in (" + Const.IdsToIn(bids) + ")                            ");
			sb.append("   GROUP BY	a.bid                                     ");
			sb.append(" ORDER BY   replyDate DESC                              ");
			String sql = Const.RemoveExtraSpaces(sb.toString());
			Query queryObject = getSession().createSQLQuery(sql);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			queryObject.setFirstResult(pageNumber * numPerPage);
			queryObject.setMaxResults(numPerPage);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	/**
	 * 获取最新回复的几条
	 * 
	 * @param pageNumber
	 * @param numPerPage
	 * @param startDate
	 * @param endDate
	 * @param myUid
	 * @param bids
	 * @param num
	 * @param date
	 * @param direction
	 * @return
	 * @createTime 2015-5-9 下午10:10:25
	 */
	public List findByPropertyPaging(String bids) {
		log.debug("finding Bills,BillsBio,replys instance limit pageNumber * numPerPage");
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT                                                   ");
			sb.append(" 	a.*, b.bioname,                                      ");
			sb.append(" 	b.bsign,                                             ");
			sb.append(" 	c.btypename,                                         ");
			sb.append(" 	a.bimageid,                                          ");
			sb.append(" 	'' bidirs,                                           ");
			sb.append(" 	IFNULL(d.bidir, 'null') AS bidir,                    ");
			sb.append(" 	e.id AS likeid                                       ");
			sb.append(" FROM                                                     ");
			sb.append(" 	(                                                    ");
			sb.append(" 		(                                                ");
			sb.append(" 			(                                            ");
			sb.append(" 				SELECT                                   ");
			sb.append(" 					x.bid,                               ");
			sb.append(" 					x.bio,                               ");
			sb.append(" 					x.bamount,                           ");
			sb.append(" 					x.btype,                             ");
			sb.append(" 					x.bimageid,                          ");
			sb.append(" 					x.bdate,                             ");
			sb.append(" 					x.bcaption,                          ");
			sb.append(" 					x.bbetravelleader,                   ");
			sb.append(" 					x.btid,                              ");
			sb.append(" 					x.replys,                            ");
			sb.append(" 					x.forwards,                          ");
			sb.append(" 					x.username,                          ");
			sb.append(" 					x.uid,                               ");
			sb.append(" 					x.bctype,                            ");
			sb.append(" 					x.rootbid,                           ");
			sb.append(" 					x.like_count                         ");
			sb.append(" 				FROM                                     ");
			sb.append(" 					bills x                              ");
			sb.append(" 				WHERE                                    ");
			sb.append("   x.bid in (" + Const.IdsToIn(bids) + ")                 ");
			sb.append(" 				AND (x.bctype = 0 OR x.bctype = 2)       ");
			sb.append(" 				AND isdeleted = 0                        ");
			sb.append(" 			) a                                          ");
			sb.append(" 			LEFT JOIN bills_bio b ON a.bio = b.bio       ");
			sb.append(" 		)                                                ");
			sb.append(" 		LEFT JOIN bills_type c ON c.btypeid = a.btype    ");
			sb.append(" 	)                                                    ");
			sb.append(" LEFT JOIN bills_images d ON d.biid = a.bimageid          ");
			sb.append(" LEFT JOIN bills_like e ON e.uid = '7'                    ");
			sb.append(" AND e.bid = a.bid                                        ");
			sb.append(" ORDER BY                                                 ");
			sb.append(" 	a.bdate DESC                                         ");

			String sql = Const.RemoveExtraSpaces(sb.toString());
			Query queryObject = getSession().createSQLQuery(sql);
			queryObject.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}
}