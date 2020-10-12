/**
 * 
 * @auther wuwang
 * @createTime 2014-11-29 上午1:02:04
 */
package com.javasrc.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

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
public class MessageService {
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

	public MessageService() {
		super();
		setRelationshipFriends(new RelationshipFriends());
		setRelationshipFriendsDao(new RelationshipFriendsDAO());
		setUsers(new Users());
		setUsersDao(new UsersDAO());
		setMessageBean(new MessageBean());
	}

	public List<MessageBean> getMessage(int uid) {
		List<MessageBean> list1 = getFriendsMessage(uid);

		return list1;
	}

	public List<MessageBean> getFriendsMessage(int uid) {
		try {
			DetachedCriteria dc = DetachedCriteria.forClass(RelationshipFriends.class);
			dc.add(Restrictions.eq("userId2", uid)).add(Restrictions.eq("status", (short) 1));
			List<RelationshipFriends> list1 = dc.getExecutableCriteria(getRelationshipFriendsDao().getSession()).list();
			if (Const.ListIsNotBlank(list1)) {
				ArrayList<MessageBean> list = new ArrayList<MessageBean>();
				HashMap users = new HashMap();
				HashSet usersSet = new HashSet();
				for (RelationshipFriends s : list1) {
					usersSet.add(s.getUserId1());
				}
				dc = DetachedCriteria.forClass(Users.class);
				dc.add(Restrictions.in("uid", usersSet));
				List<Users> list2 = dc.getExecutableCriteria(getUsersDao().getSession()).list();
				if (Const.ListIsNotBlank(list2)) {
					for (Users s : list2) {
						users.put(s.getUid(), s.getUsername());
					}
				} else {
					return null;
				}
				for (RelationshipFriends s : list1) {
					MessageBean ms = new MessageBean();
					ms.setTitle((String) users.get(s.getUserId1()));
					ms.setMessage("申请好友关系");
					ms.setAction("a=33&uid1=" + s.getUserId1() + "&uid2=" + s.getUserId2() + "||" + "a=36&uid1=" + s.getUserId1() + "&uid2=" + s.getUserId2());
					ms.setActionName("");
					ms.setImg("image/avatar/avatar_uid_" + s.getUserId1() + ".jpg");
					ms.setDate(s.getDatetime());
					list.add(ms);
				}
				return list;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
