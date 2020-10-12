/**
 * 
 * @auther wuwang
 * @createTime 2014-11-29 上午1:02:04
 */
package com.javasrc.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.servlet.ServletException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.beans.MessageBean;
import com.hibernate.voDao.RelationshipFriends;
import com.hibernate.voDao.RelationshipFriendsDAO;
import com.hibernate.voDao.Users;
import com.hibernate.voDao.UsersDAO;
import com.staticClass.Const;

/**
 * 
 * 
 * @author peaches
 */
public class FriendsService {
	private RelationshipFriends relationshipFriends;
	private RelationshipFriendsDAO relationshipFriendsDao;
	private Users users;
	private UsersDAO usersDao;
	private MessageBean messageBean;

	public RelationshipFriends getRelationshipFriends() {
		return relationshipFriends;
	}

	public void setRelationshipFriends(RelationshipFriends relationshipFriends) {
		this.relationshipFriends = relationshipFriends;
	}

	public RelationshipFriendsDAO getRelationshipFriendsDao() {
		return relationshipFriendsDao;
	}

	public void setRelationshipFriendsDao(RelationshipFriendsDAO relationshipFriendsDao) {
		this.relationshipFriendsDao = relationshipFriendsDao;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

	public UsersDAO getUsersDao() {
		return usersDao;
	}

	public void setUsersDao(UsersDAO usersDao) {
		this.usersDao = usersDao;
	}

	public MessageBean getMessageBean() {
		return messageBean;
	}

	public void setMessageBean(MessageBean messageBean) {
		this.messageBean = messageBean;
	}

	public FriendsService() {
		super();
		setRelationshipFriends(new RelationshipFriends());
		setRelationshipFriendsDao(new RelationshipFriendsDAO());
		setUsers(new Users());
		setUsersDao(new UsersDAO());
		setMessageBean(new MessageBean());
	}

	public List searchPerson(String searchWord) {
		List list1 = getUsersDao().findByKeyWords(searchWord);
		return list1;
	}

	public int addFriends(int uid1, int uid2) {
		try {
			if (uid1 == uid2) {
				return 0;// 不能添加自己为好友
			}
			setUsers(getUsersDao().findById(Integer.valueOf(uid1)));
			DetachedCriteria dc = DetachedCriteria.forClass(RelationshipFriends.class);
			dc.add(Restrictions.or(Restrictions.and(Restrictions.eq("userId1", uid1), Restrictions.eq("userId2", uid2)),
					Restrictions.and(Restrictions.eq("userId1", uid2), Restrictions.eq("userId2", uid1))));
			List<RelationshipFriends> list = dc.getExecutableCriteria(getRelationshipFriendsDao().getSession()).list();
			if (Const.ListIsNotBlank(list)) {
				if (list.get(0).getStatus() == 1) {
					return 1;// 已经申请好友，等待回复
				} else if (list.get(0).getStatus() == 2) {
					return 2;// 已经申请好友，等待回复
				}
			}
			getRelationshipFriends().setUserId1(uid2);// 1+2好友
			getRelationshipFriends().setUserId2(getUsers().getUid());
			getRelationshipFriends().setStatus((short) 1);// 1为加好友，2为已经是好友。见数据库设计文档
			getRelationshipFriends().setDatetime(new Date());
			getRelationshipFriendsDao().save(getRelationshipFriends());

			return 3;// 成功
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;// 添加好友失败
	}

	/**
	 * 获取加好友信息
	 * 
	 * @createTime 2014-11-29 上午12:35:57
	 */
	public List<MessageBean> applyFriends(int uid) throws ServletException, IOException {
		getRelationshipFriends().setStatus((short) 1);
		getRelationshipFriends().setUserId2(uid);
		List<RelationshipFriends> list1 = getRelationshipFriendsDao().findByExample(getRelationshipFriends());

		List<MessageBean> list = new ArrayList<MessageBean>();
		if (list1 != null && list1.size() > 0 && list1.get(0) != null) {
			for (int i = 0; i < list1.size(); i++) {
				getMessageBean().setTitle("好友申请");
				setUsers(getUsersDao().findById(list1.get(i).getUserId1()));
				getMessageBean().setMessage(getUsers().getUsername() + "申请添加你为好友");
				getMessageBean().setAction("Friends.yy?a=confirmFriends&uid1=" + getUsers().getUid());
				getMessageBean().setActionName("确认好友");// 多个操作
				list.add(getMessageBean());
			}
		}

		return list;
	}

	public boolean confirmFriends(int uid1, int uid2) {
		try {
			getRelationshipFriends().setStatus((short) 1);
			getRelationshipFriends().setUserId1(uid1);
			getRelationshipFriends().setUserId2(uid2);
			List<RelationshipFriends> list1 = getRelationshipFriendsDao().findByExample(getRelationshipFriends());
			if (list1 != null && list1.size() > 0 && list1.get(0) != null) {
				list1.get(0).setStatus((short) 2);
				getRelationshipFriendsDao().save(list1.get(0));
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean refuseFriends(int uid1, int uid2) {
		try {
			getRelationshipFriends().setStatus((short) 1);
			getRelationshipFriends().setUserId1(uid1);
			getRelationshipFriends().setUserId2(uid2);
			List<RelationshipFriends> list1 = getRelationshipFriendsDao().findByExample(getRelationshipFriends());
			if (list1 != null && list1.size() > 0 && list1.get(0) != null) {
				list1.get(0).setStatus((short) 3);// 拒绝
				getRelationshipFriendsDao().save(list1.get(0));
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Users> myFriends(int uid1) {
		try {
			getRelationshipFriends().setStatus((short) 2);
			getRelationshipFriends().setUserId1(uid1);
			getRelationshipFriends().setUserId2(null);
			List<RelationshipFriends> list1 = getRelationshipFriendsDao().findByExample(getRelationshipFriends());
			getRelationshipFriends().setUserId1(null);
			getRelationshipFriends().setUserId2(uid1);
			List<RelationshipFriends> list2 = getRelationshipFriendsDao().findByExample(getRelationshipFriends());
			list1.addAll(list2);
			if (list1 != null && list1.size() > 0 && list1.get(0) != null) {
				HashSet ids = new HashSet();
				for (RelationshipFriends s : list1) {
					ids.add(s.getUserId1().equals(uid1) ? null : s.getUserId1());
					ids.add(s.getUserId2().equals(uid1) ? null : s.getUserId2());
				}
				ids.add(999);// 图灵机器人
				return getUsersDao().findByIds(ids);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean deleteFriends(int uid1, int uid2) {
		try {
			getRelationshipFriends().setStatus((short) 2);
			getRelationshipFriends().setUserId1(uid1);
			getRelationshipFriends().setUserId2(uid2);
			List<RelationshipFriends> list1 = getRelationshipFriendsDao().findByExample(getRelationshipFriends());
			getRelationshipFriends().setUserId1(uid2);
			getRelationshipFriends().setUserId2(uid1);
			List<RelationshipFriends> list2 = getRelationshipFriendsDao().findByExample(getRelationshipFriends());
			list1.addAll(list2);
			if (list1 != null && list1.size() > 0 && list1.get(0) != null) {
				getRelationshipFriendsDao().delete(list1.get(0));
				getRelationshipFriendsDao().getSession().flush();
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
