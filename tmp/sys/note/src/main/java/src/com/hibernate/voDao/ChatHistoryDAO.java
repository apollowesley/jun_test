package com.hibernate.voDao;

import java.util.List;

import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.criterion.Example;
import org.hibernate.transform.Transformers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.staticClass.Const;

/**
 * A data access object (DAO) providing persistence and search support for
 * ChatHistory entities. Transaction control of the save(), update() and
 * delete() operations can directly support Spring container-managed
 * transactions or they can be augmented to handle user-managed Spring
 * transactions. Each of these methods provides additional information for how
 * to configure it for the desired type of transaction control.
 * 
 * @see com.hibernate.voDao.ChatHistory
 * @author MyEclipse Persistence Tools
 */
public class ChatHistoryDAO extends BaseHibernateDAO implements DAO<ChatHistory> {
	private static final Logger log = LoggerFactory.getLogger(ChatHistoryDAO.class);
	// property constants
	public static final String UID_SEND = "uidSend";
	public static final String UID_RECEIVE = "uidReceive";
	public static final String MESSAGE_CONTENT = "messageContent";
	public static final String MESSAGE_STATUS = "messageStatus";
	public static final String MESSAGE_TYPE = "messageType";

	@Override
	public void save(ChatHistory transientInstance) {
		log.debug("saving ChatHistory instance");
		try {
			getSession().saveOrUpdate(transientInstance);
			log.debug("save successful");
		} catch (RuntimeException re) {
			log.error("save failed", re);
			throw re;
		}
	}

	@Override
	public void delete(ChatHistory persistentInstance) {
		log.debug("deleting ChatHistory instance");
		try {
			getSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public ChatHistory findById(Long id) {
		log.debug("getting ChatHistory instance with id: " + id);
		try {
			ChatHistory instance = (ChatHistory) getSession().get("com.hibernate.voDao.ChatHistory", id);
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	@Override
	public List findByExample(ChatHistory instance) {
		log.debug("finding ChatHistory instance by example");
		try {
			List results = getSession().createCriteria("com.hibernate.voDao.ChatHistory").add(Example.create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}

	@Override
	public List findByProperty(String propertyName, Object value) {
		log.debug("finding ChatHistory instance with property: " + propertyName + ", value: " + value);
		try {
			String queryString = "from ChatHistory as model where model." + propertyName + "= ?";
			Query queryObject = getSession().createQuery(queryString);
			queryObject.setParameter(0, value);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find by property name failed", re);
			throw re;
		}
	}

	public List findByUidSend(Object uidSend) {
		return findByProperty(UID_SEND, uidSend);
	}

	public List findByUidReceive(Object uidReceive) {
		return findByProperty(UID_RECEIVE, uidReceive);
	}

	public List findByMessageContent(Object messageContent) {
		return findByProperty(MESSAGE_CONTENT, messageContent);
	}

	public List findByMessageStatus(Object messageStatus) {
		return findByProperty(MESSAGE_STATUS, messageStatus);
	}

	public List findByMessageType(Object messageType) {
		return findByProperty(MESSAGE_TYPE, messageType);
	}

	@Override
	public List findAll() {
		log.debug("finding all ChatHistory instances");
		try {
			String queryString = "from ChatHistory";
			Query queryObject = getSession().createQuery(queryString);
			return queryObject.list();
		} catch (RuntimeException re) {
			log.error("find all failed", re);
			throw re;
		}
	}

	@Override
	public ChatHistory merge(ChatHistory detachedInstance) {
		log.debug("merging ChatHistory instance");
		try {
			ChatHistory result = (ChatHistory) getSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	@Override
	public void attachDirty(ChatHistory instance) {
		log.debug("attaching dirty ChatHistory instance");
		try {
			getSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public void attachClean(ChatHistory instance) {
		log.debug("attaching clean ChatHistory instance");
		try {
			getSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@Override
	public ChatHistory findById(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * 根据两个 用户id查询聊天记录，startDate是时间限制，direction是向前还是向后搜索,true,更早，false,更晚
	 * 
	 * @param uid1
	 * @param uid2
	 * @param startDate
	 * @param direction
	 * @return
	 * @createTime 2014-12-28 上午12:32:35
	 */
	public List getChatHistoryByUsers(String uid1, String uid2, String startDate, boolean direction) {
		log.debug("getChatHistoryByUsers ChatHistory ");
		try {
			StringBuilder sb = new StringBuilder();
			sb.append(" SELECT                                                  ");
			sb.append(" 	u1.uid uid1,                                        ");
			sb.append(" 	u2.uid uid2,                                        ");
			sb.append(" 	u1.username username1,                              ");
			sb.append(" 	u2.username username2,                              ");
			sb.append(" 	ch.id,                                              ");
			sb.append(" 	ch.message_content,                                 ");
			sb.append(" 	cast(SUBSTR(FROM_UNIXTIME(ch.message_timestamp / 1000),1,23) as char) AS message_datetime,                                ");
			sb.append(" 	ch.message_timestamp,                               ");
			sb.append(" 	ch.message_status,                                  ");
			sb.append(" 	ch.message_type,                                    ");
			sb.append(" 	ch.display_datetime                                 ");
			sb.append(" FROM                                                    ");
			sb.append(" 	(                                                   ");
			sb.append(" 		SELECT                                          ");
			sb.append(" 			ch.uid_send,                                ");
			sb.append(" 			ch.uid_receive,                             ");
			sb.append(" 			ch.id,                                      ");
			sb.append(" 			ch.message_content,                         ");
			sb.append(" 			ch.message_datetime,                        ");
			sb.append(" 			ch.message_timestamp,                       ");
			sb.append(" 			ch.message_status,                          ");
			sb.append(" 			ch.message_type,                            ");
			sb.append(" 			ch.display_datetime                         ");
			sb.append(" 		FROM                                            ");
			sb.append(" 			chat_history ch,                            ");
			sb.append(" 			users u1,                                   ");
			sb.append(" 			users u2                                    ");
			sb.append(" 		WHERE                                           ");
			sb.append(" 			(                                           ");
			sb.append(" 				(                                       ");
			sb.append(" 					ch.uid_send = u1.uid                ");
			sb.append(" 					AND ch.uid_receive = u2.uid         ");
			sb.append(" 				)                                       ");
			sb.append(" 				OR (                                    ");
			sb.append(" 					ch.uid_send = u2.uid                ");
			sb.append(" 					AND ch.uid_receive = u1.uid         ");
			sb.append(" 				)                                       ");
			sb.append(" 			)                                           ");
			sb.append(" AND u1.uid = '" + uid1 + "'                          ");
			sb.append(" AND u2.uid = '" + uid2 + "'                          ");
			if (!Const.stringIsEmpty(startDate)) {
				sb.append(" AND ch.message_timestamp " + (direction ? " < '" : " > '") + startDate + "'        ");
			}
			sb.append(" order by ch.message_timestamp desc LIMIT 9     ");
			sb.append(" 	) ch                                                ");
			sb.append(" INNER JOIN users u1 ON u1.uid = ch.uid_send             ");
			sb.append(" INNER JOIN users u2 ON u2.uid = ch.uid_receive    ");
			sb.append(" order by ch.message_timestamp asc     ");
			String sql = Const.RemoveExtraSpaces(sb.toString());
			SQLQuery query = getSession().createSQLQuery(sql);
			query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			List result = query.list();
			log.debug("getChatHistoryByUsers successful");
			return result;
		} catch (RuntimeException re) {
			log.error("getChatHistoryByUsers failed", re);
			throw re;
		}
	}
}